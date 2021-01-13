package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.OmsCartItem;

import java.util.List;

/**
 * @author shkstart
 * @create 2021-01-08 16:14
 */
public interface CartService {
    public OmsCartItem ifCartExistByUser(String memberId, long skuId);

    void flushCartCache(String memberId);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void addCart(OmsCartItem omsCartItem);

    List<OmsCartItem> cartList(String memberId);

    void checkCart(OmsCartItem omsCartItem);
}
