package com.kgc.kmall01.search.controller;

import com.kgc.kmall01.bean.*;
import com.kgc.kmall01.service.AttrService;
import com.kgc.kmall01.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author shkstart
 * @create 2021-01-06 13:14
 */
@CrossOrigin
@Controller
public class SearchController {
    @Reference
    SearchService searchService;
    @Reference
    AttrService attrService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/list.html")
    public String list(PmsSearchSkuParam pmsSearchSkuParam, Model model) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = searchService.list(pmsSearchSkuParam);
        model.addAttribute("skuLsInfoList", pmsSearchSkuInfos);

        Set<Long> valuesIdSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSearchSkuInfo.getSkuAttrValueList()) {
                valuesIdSet.add(pmsSkuAttrValue.getValueId());
            }
        }
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.selectByValueId(valuesIdSet);


        //已选中的valueId
        String[] valueId = pmsSearchSkuParam.getValueId();

        //封装面包屑路径
        if(valueId!= null){
            List<PmsSearchCrumb> pmsSearchCrumbList = new ArrayList<>();
            for (String s : valueId) {
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setValueId(s);
                pmsSearchCrumb.setUrlParam(getURLParam(pmsSearchSkuParam,s));
                String attrValue = getAttrValue(pmsBaseAttrInfos, s);
                pmsSearchCrumb.setValueName(attrValue);
                pmsSearchCrumbList.add(pmsSearchCrumb);
            }
            model.addAttribute("attrValueSelectedList", pmsSearchCrumbList);
        }

        if(valueId!=null){
            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
            while (iterator.hasNext()){
                PmsBaseAttrInfo next = iterator.next();
                for (PmsBaseAttrValue pmsBaseAttrValue : next.getAttrValueList()) {
                    for (String s : valueId) {
                        if(s.equals(pmsBaseAttrValue.getId().toString())){
                            iterator.remove();
                        }
                    }
                }
            }
        }






        model.addAttribute("attrList", pmsBaseAttrInfos);
        //拼接平台url
        String urlParam = getURLParam(pmsSearchSkuParam);
        model.addAttribute("urlParam",urlParam);

        //显示关键字
        model.addAttribute("keyword",pmsSearchSkuParam.getKeyword());

        return "list";
    }

    //根据参数拼接url参数
    public String getURLParam(PmsSearchSkuParam pmsSearchSkuParam) {
        StringBuffer stringBuffer = new StringBuffer();

        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
        String[] valueId = pmsSearchSkuParam.getValueId();


        if (StringUtils.isNotBlank(catalog3Id)) {
            stringBuffer.append("&catalog3Id=" + catalog3Id);
        }

        if (StringUtils.isNotBlank(keyword)) {
            stringBuffer.append("&keyword=" + keyword);
        }

        if (valueId != null) {
            for (String s : valueId) {
                stringBuffer.append("&valueId=" + s);
            }
        }

        return stringBuffer.substring(1);
    }

    //根拼接面包屑url
    public String getURLParam(PmsSearchSkuParam pmsSearchSkuParam,String vid ){
        StringBuffer stringBuffer = new StringBuffer();
        String keyword = pmsSearchSkuParam.getKeyword();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String[] valueId = pmsSearchSkuParam.getValueId();

        if(StringUtils.isNotBlank(keyword)){
            stringBuffer.append("&keyword="+keyword);
        }

        if(StringUtils.isNotBlank(catalog3Id)){
            stringBuffer.append("&catalog3Id="+catalog3Id);
        }

        if(valueId!=null){
            for (String s : valueId) {
                if (s.equals(vid) == false){
                    stringBuffer.append("&valueId="+s);
                }
            }
        }
        return stringBuffer.substring(1);
    }

    //显示平台属性和平台属性值
    public String getAttrValue(List<PmsBaseAttrInfo> pmsBaseAttrInfos,String vid ){
        String valueName = "";
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            for (PmsBaseAttrValue baseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                if(vid.equals(baseAttrValue.getId().toString())){
                    valueName = pmsBaseAttrInfo.getAttrName()+" : "+ baseAttrValue.getValueName();
                    return valueName;
                }
            }
        }

        return valueName;
    }
}
