package com.kgc.kmall01.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author shkstart
 * @create 2021-01-04 16:56
 */
public class PmsSearchSkuParam implements Serializable {
    private String keyword;
    private String catalog3Id;
    private List<PmsSkuAttrValue> skuAttrValueList;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }
}
