package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.PmsSkuInfo;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-29 18:44
 */
public interface SkuService {
    public String saveSkuInfo(PmsSkuInfo skuInfo);

    PmsSkuInfo seltBySkuIds(Long skuId);


    List<PmsSkuInfo> selectSpuId(Long spuId);
}
