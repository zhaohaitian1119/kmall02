package com.kgc.kmall01.user.controller;

import com.kgc.kmall01.bean.Member;
import com.kgc.kmall01.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-15 19:48
 */
@Controller
public class MemberController {
    @Reference
    MemberService memberService;

    @RequestMapping("/test")
    @ResponseBody
    public List<Member> test(){
        return memberService.selAll();
    }

}
