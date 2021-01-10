package com.kgc.kmall01.kmallcartservice.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall01.bean.OmsCartItem;
import com.kgc.kmall01.bean.OmsCartItemExample;
import com.kgc.kmall01.kmallcartservice.mapper.OmsCartItemMapper;
import com.kgc.kmall01.service.CartService;
import com.kgc.kmall01.utils.RedisUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2021-01-08 16:15
 */
@Component
@Service
public class CartServiceImpl implements CartService {

    @Resource
    OmsCartItemMapper omsCartItemMapper;
    @Resource
    RedisUtils redisUtils;

    @Override
    public OmsCartItem ifCartExistByUser(String memberId, long skuId) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        Long id = Long.parseLong(memberId);
        criteria.andMemberIdEqualTo(id);
        criteria.andProductSkuIdEqualTo(skuId);
        List<OmsCartItem> omsCartItemList = omsCartItemMapper.selectByExample(example);
        if(omsCartItemList!=null&&omsCartItemList.size()>0){
            return omsCartItemList.get(0);
        }else{
            return null;
        }


    }

    @Override
    public void flushCartCache(String memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(Long.valueOf(memberId));
        List<OmsCartItem> omsCartItemList = omsCartItemMapper.selectByExample(example);

        //同步到reids
        Jedis jedis = redisUtils.getJedis();
        Map<String,String> map = new HashMap<>();
        for (OmsCartItem cartItem : omsCartItemList) {
            map.put(cartItem.getProductSkuId().toString(), JSON.toJSONString(cartItem));
        }

        jedis.del("user:"+memberId+":cart");
        jedis.hmset("user:"+memberId+":cart",map);

        jedis.close();
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {
        omsCartItemMapper.updateByPrimaryKeySelective(omsCartItemFromDb);
    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        omsCartItemMapper.insertSelective(omsCartItem);
    }

    @Override
    public List<OmsCartItem> cartList(String memberId) {
        Jedis jedis = null;
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        try {
            jedis = redisUtils.getJedis();
            List<String> hvals = jedis.hvals("user:" + memberId + ":cart");
            for (String hval : hvals) {
                OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
                omsCartItems.add(omsCartItem);
            }
        }catch (Exception e){
            // 处理异常，记录系统日志
            e.printStackTrace();
            //String message = e.getMessage();
            //logService.addErrLog(message);
            return null;
        }finally {
            jedis.close();
        }

        return omsCartItems;
    }
}
