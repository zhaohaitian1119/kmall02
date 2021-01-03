package com.kgc.kmall01.manager.service;


import com.alibaba.fastjson.JSON;
import com.kgc.kmall01.bean.PmsSkuAttrValue;
import com.kgc.kmall01.bean.PmsSkuImage;
import com.kgc.kmall01.bean.PmsSkuInfo;
import com.kgc.kmall01.bean.PmsSkuSaleAttrValue;
import com.kgc.kmall01.manager.mapper.PmsSkuAttrValueMapper;
import com.kgc.kmall01.manager.mapper.PmsSkuImageMapper;
import com.kgc.kmall01.manager.mapper.PmsSkuInfoMapper;
import com.kgc.kmall01.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.kgc.kmall01.manager.utils.RedisUtils;
import com.kgc.kmall01.service.SkuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author shkstart
 * @create 2020-12-24 8:42
 */
@Component
@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Resource
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Resource
    PmsSkuImageMapper pmsSkuImageMapper;
    @Resource
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Resource
    RedisUtils redisUtils;

    @Override
    public String saveSkuInfo(PmsSkuInfo skuInfo) {

        pmsSkuInfoMapper.insert(skuInfo);

        List<PmsSkuImage> skuImageList = skuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuInfo.getId());
            pmsSkuImageMapper.insert(pmsSkuImage);
        }

        List<PmsSkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuInfo.getId());
            pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
        }

        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuInfo.getId());
            pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
        }


        return "success";
    }

    @Override
    public PmsSkuInfo seltBySkuIds(Long skuId) {
        PmsSkuInfo pmsSkuInfo = null;
        Jedis jedis = redisUtils.getJedis();
        //命名一个key
        String key = "sku:" + skuId + ":info";
        String skuJSON = JSON.toJSONString(key);
        if (skuJSON != null) {
            //缓存中有数据
            pmsSkuInfo = JSON.parseObject(skuJSON, PmsSkuInfo.class);
            jedis.close();
            return pmsSkuInfo;
        } else {
            //设置nx分布式锁 防止缓存击穿
            String skukeylock = "sku:" + skuId + ":lock";
            String skuvaluelock = UUID.randomUUID().toString();
            //设置分布式锁的key
            String ok = jedis.set(skukeylock, skuvaluelock, "NX", "PX", 60 * 1000);
            if (ok.equals("OK")) {


                //缓存中没有数据
                pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
                //防止缓存穿透
                if (pmsSkuInfo != null) {
                    //保存到redis
                    String skuInfoJsonStr = JSON.toJSONString(pmsSkuInfo);
                    //设置有效期防止缓存雪崩
                    Random random = new Random();
                    int i = random.nextInt() * 10;
                    jedis.setex(key, i * 60 * 1000, skuInfoJsonStr);

                } else {
                    jedis.setex(key, 60 * 1000, "empty");
                }
                //在数据库拿数据后删除分布式锁
                String skuvaluelock2 =  jedis.get(skuvaluelock);
                if(skuvaluelock2.equals(skuvaluelock) && skuvaluelock2!=null){
                    jedis.del(skukeylock);
                }
            }else{
                //没拿到分布式锁 睡眠3秒
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            jedis.close();
            return pmsSkuInfo;
        }

    }

    @Override
    public List<PmsSkuInfo> selectSpuId(Long spuId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectBySpuId(spuId);
        return pmsSkuInfos;
    }
}
