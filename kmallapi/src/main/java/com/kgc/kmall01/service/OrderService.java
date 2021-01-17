package com.kgc.kmall01.service;

/**
 * @author shkstart
 * @create 2021-01-17 12:58
 */
public interface OrderService {
    String genTradeCode(Long aLong);

    String checkTradeCode(Long aLong, String tradeCode);
}
