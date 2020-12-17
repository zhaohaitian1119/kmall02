package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.PmsBaseSaleAttr;
import com.kgc.kmall01.bean.PmsProductInfo;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 18:48
 */
public interface SpuService {
    public List<PmsProductInfo> spuList(Long catalog3Id);

    public List<PmsBaseSaleAttr> baseSaleAttrList();
}
