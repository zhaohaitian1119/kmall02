package com.kgc.kmall01.user.service;

import com.kgc.kmall01.bean.Member;
import com.kgc.kmall01.service.MemberService;
import com.kgc.kmall01.user.mapper.MemberMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-15 19:40
 */
@Component
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberMapper memberMapper;

    @Override
    public List<Member> selAll() {
        return memberMapper.selectByExample(null);
    }
}
