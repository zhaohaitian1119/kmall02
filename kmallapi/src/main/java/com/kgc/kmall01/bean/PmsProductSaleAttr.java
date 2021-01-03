package com.kgc.kmall01.bean;

import java.io.Serializable;
import java.util.List;

public class PmsProductSaleAttr implements Serializable {
    private Long id;

    private Long productId;

    private Long saleAttrId;

    private String saleAttrName;

    private List<PmsProductSaleAttrValue> spuSaleAttrValueList;

    private Long isChecked;

    public Long getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Long isChecked) {
        this.isChecked = isChecked;
    }

    public List<PmsProductSaleAttrValue> getSpuSaleAttrValueList() {
        return spuSaleAttrValueList;
    }

    public void setSpuSaleAttrValueList(List<PmsProductSaleAttrValue> spuSaleAttrValueList) {
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(Long saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName == null ? null : saleAttrName.trim();
    }
}