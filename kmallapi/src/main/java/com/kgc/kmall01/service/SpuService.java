package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.PmsBaseSaleAttr;
import com.kgc.kmall01.bean.PmsProductImage;
import com.kgc.kmall01.bean.PmsProductInfo;
import com.kgc.kmall01.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 18:48
 */
public interface SpuService {
    public List<PmsProductInfo> spuList(Long catalog3Id);

    public List<PmsBaseSaleAttr> baseSaleAttrList();

    public Integer saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> spuImageList(Long spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(Long spuId);

    //回显销售属性和销售属性值
    List<PmsProductSaleAttr> spuSaleAttrListIsCheck(Long spuId,Long skuId);
}
