package com.kgc.kmall01.manager.controller;

import com.kgc.kmall01.bean.PmsBaseAttrInfo;
import com.kgc.kmall01.bean.PmsBaseAttrInfoExample;
import com.kgc.kmall01.bean.PmsBaseAttrValue;
import com.kgc.kmall01.service.AttrService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 18:51
 */
@CrossOrigin
@RestController
public class AttrController {

    @Reference
    AttrService attrService;



    @RequestMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(Long catalog3Id){
        List<PmsBaseAttrInfo> select = attrService.select(catalog3Id);
        return select;
    }
    @RequestMapping("/saveAttrInfo")
    public Integer saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo){
        Integer i = attrService.updateAttrValue(attrInfo);
        return i;
    }

    @RequestMapping("/getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(Long attrId){
        List<PmsBaseAttrValue> attrValueList = attrService.selByAttrId(attrId);
        return attrValueList;
    }

}
