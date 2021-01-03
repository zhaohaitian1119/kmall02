package com.kgc.kmall02.litemweb.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall01.bean.PmsProductSaleAttr;
import com.kgc.kmall01.bean.PmsSkuInfo;
import com.kgc.kmall01.bean.PmsSkuSaleAttrValue;
import com.kgc.kmall01.service.SkuService;
import com.kgc.kmall01.service.SpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-12-29 14:13
 */
@CrossOrigin
@Controller
public class ItemController {


    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;


    @RequestMapping("{skuId}.html")
    public String item(@PathVariable Long skuId, Model model){
        PmsSkuInfo pmsSkuInfo = skuService.seltBySkuIds(skuId);
        model.addAttribute("skuInfo",pmsSkuInfo);

        //根据spuid获取销售属性和属性和属性值
        List<PmsProductSaleAttr> spuSaleAttrListCheckBySku=spuService.spuSaleAttrListIsCheck(pmsSkuInfo.getSpuId(),pmsSkuInfo.getId());
        model.addAttribute("spuSaleAttrListCheckBySku",spuSaleAttrListCheckBySku);

        //根据spuid获得skuid和销售属性值id的对应关系，并拼接成字符串
        List<PmsSkuInfo> pmsSkuInfos = skuService.selectSpuId(pmsSkuInfo.getSpuId());
        Map<String, String> skuSaleAttrHash = new HashMap<>();
        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String k = "";
            String v=skuInfo.getId()+"";
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
                k+=pmsSkuSaleAttrValue.getSaleAttrValueId()+"|";
            }
            skuSaleAttrHash.put(k,v);
        }
        // 将sku的销售属性hash表放到页面
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        model.addAttribute("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);


        return "item";
    }

}
