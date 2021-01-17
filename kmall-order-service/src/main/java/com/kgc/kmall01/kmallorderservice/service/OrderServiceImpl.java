package com.kgc.kmall01.kmallorderservice.service;

import com.kgc.kmall01.service.OrderService;
import com.kgc.kmall01.utils.RedisUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author shkstart
 * @create 2021-01-17 12:58
 */
@Component
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    RedisUtils redisUtil;

    @Override
    public String genTradeCode(Long MemberId) {
        Jedis jedis = redisUtil.getJedis();

        String tradeKey  =  "user:"+MemberId+":tradeCode";

        String tradeCode = UUID.randomUUID().toString();

        jedis.setex(tradeKey, 60 * 60 * 2, tradeCode);

        jedis.close();

        return tradeCode;
    }

    @Override
    public String checkTradeCode(Long MemberId, String tradeCode) {
        Jedis jedis = redisUtil.getJedis();

        String tradeKey  =  "user:"+MemberId+":tradeCode";

        String s = jedis.get(tradeKey);

        jedis.close();

        if(s!=null&&s.equals(tradeCode)){
            return "success";
        }else{
            return "fail";
        }

    }
}
