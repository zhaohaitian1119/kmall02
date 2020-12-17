package com.kgc.kmall01.manager.mapper;

import com.kgc.kmall01.bean.pmsProductSaleAttrValue;
import com.kgc.kmall01.bean.pmsProductSaleAttrValueExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface pmsProductSaleAttrValueMapper {
    int countByExample(pmsProductSaleAttrValueExample example);

    int deleteByExample(pmsProductSaleAttrValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(pmsProductSaleAttrValue record);

    int insertSelective(pmsProductSaleAttrValue record);

    List<pmsProductSaleAttrValue> selectByExample(pmsProductSaleAttrValueExample example);

    pmsProductSaleAttrValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") pmsProductSaleAttrValue record, @Param("example") pmsProductSaleAttrValueExample example);

    int updateByExample(@Param("record") pmsProductSaleAttrValue record, @Param("example") pmsProductSaleAttrValueExample example);

    int updateByPrimaryKeySelective(pmsProductSaleAttrValue record);

    int updateByPrimaryKey(pmsProductSaleAttrValue record);
}