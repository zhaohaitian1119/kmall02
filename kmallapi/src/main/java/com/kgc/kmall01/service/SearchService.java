package com.kgc.kmall01.service;

import com.kgc.kmall01.bean.PmsBaseAttrInfo;
import com.kgc.kmall01.bean.PmsSearchSkuInfo;
import com.kgc.kmall01.bean.PmsSearchSkuParam;

import java.util.List;
import java.util.Set;

/**
 * @author shkstart
 * @create 2021-01-05 19:31
 */
public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchSkuParam pmsSearchSkuParam);


}
