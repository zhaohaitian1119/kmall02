package com.kgc.kmall01.manager.service;

import com.kgc.kmall01.bean.*;
import com.kgc.kmall01.manager.mapper.PmsBaseAttrInfoMapper;
import com.kgc.kmall01.manager.mapper.PmsBaseAttrValueMapper;
import com.kgc.kmall01.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author shkstart
 * @create 2020-12-16 18:48
 */
@Component
@Service
public class AttrServiceImpl implements AttrService {

    @Resource
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Resource
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> select(Long catalog3Id) {
        PmsBaseAttrInfoExample example = new PmsBaseAttrInfoExample();
        PmsBaseAttrInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectByExample(example);
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            PmsBaseAttrValueExample example1 = new PmsBaseAttrValueExample();
            PmsBaseAttrValueExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andAttrIdEqualTo(pmsBaseAttrInfo.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByExample(example1);
            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    public List<PmsBaseAttrValue> selByAttrId(Long attrId) {
        PmsBaseAttrValueExample example = new PmsBaseAttrValueExample();
        PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
        criteria.andAttrIdEqualTo(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByExample(example);
        return pmsBaseAttrValues;
    }

    @Override
    public int updateAttrValue(PmsBaseAttrInfo pmsBaseAttrInfo) {

        try {

            if (pmsBaseAttrInfo.getId() == null) {
                pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            } else {
                pmsBaseAttrInfoMapper.updateByPrimaryKeySelective(pmsBaseAttrInfo);
                PmsBaseAttrValueExample example = new PmsBaseAttrValueExample();
                PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
                criteria.andAttrIdEqualTo(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.deleteByExample(example);
            }

            pmsBaseAttrValueMapper.insertBatch(pmsBaseAttrInfo.getId(),pmsBaseAttrInfo.getAttrValueList());

            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public List<PmsBaseAttrInfo> selectByValueId(Set<Long> valueId) {
        String join = StringUtils.join(valueId);
        join = join.substring(1, join.length() - 1);
        System.out.println(join);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectAttrInfoValueListByValueId(join);
        return pmsBaseAttrInfos;
    }
}
