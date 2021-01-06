package com.kgc.kmall01.search.controller;

import com.kgc.kmall01.bean.PmsSearchSkuInfo;
import com.kgc.kmall01.bean.PmsSearchSkuParam;
import com.kgc.kmall01.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author shkstart
 * @create 2021-01-06 13:14
 */
@CrossOrigin
@Controller
public class SearchController {
    @Reference
    SearchService searchService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/list.html")
    public String list(PmsSearchSkuParam pmsSearchSkuParam, Model model){
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = searchService.list(pmsSearchSkuParam);
        System.out.println("====");
        System.out.println("111111");
        System.out.println("====");
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            System.out.println(pmsSearchSkuInfo.toString()+1);
        }
        model.addAttribute("skuLsInfoList",pmsSearchSkuInfos);
        return "list";
    }
}
