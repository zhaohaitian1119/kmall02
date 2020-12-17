package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.PmsBaseAttrInfo;
import com.kgc.kmall01.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 18:48
 */
public interface AttrService {
    //根据三级分类id查询属性
    public List<PmsBaseAttrInfo> select(Long catalog3Id);
    //根据attrID查询属性值
    List<PmsBaseAttrValue> selByAttrId(Long attrId);

    public int updateAttrValue(PmsBaseAttrInfo  pmsBaseAttrInfo);
}
