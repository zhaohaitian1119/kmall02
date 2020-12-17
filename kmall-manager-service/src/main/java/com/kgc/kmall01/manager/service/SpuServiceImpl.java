package com.kgc.kmall01.manager.service;

import com.kgc.kmall01.bean.PmsBaseSaleAttr;
import com.kgc.kmall01.bean.PmsProductInfo;
import com.kgc.kmall01.bean.PmsProductInfoExample;
import com.kgc.kmall01.manager.mapper.PmsBaseSaleAttrMapper;
import com.kgc.kmall01.manager.mapper.PmsProductInfoMapper;
import com.kgc.kmall01.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 18:50
 */
@Component
@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    PmsProductInfoMapper pmsProductInfoMapper;
    @Resource
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        PmsProductInfoExample example = new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.selectByExample(example);
        return pmsProductInfos;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> PmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectByExample(null);
        return PmsBaseSaleAttrs;
    }
}
