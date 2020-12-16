package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.PmsBaseCatalog1;
import com.kgc.kmall01.bean.PmsBaseCatalog2;
import com.kgc.kmall01.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 18:34
 */
public interface CatalogService {
    List<PmsBaseCatalog1> selAll();

    public List<PmsBaseCatalog2> getCatalog2(Integer catalog1Id);

    public List<PmsBaseCatalog3> getCatalog3(Long catalog2Id);
}
