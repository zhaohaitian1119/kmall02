package com.kgc.kmall01.kmallpassportweb.controller;

import com.kgc.kmall01.bean.Member;
import com.kgc.kmall01.service.MemberService;
import com.kgc.kmall01.utils.JwtUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shkstart
 * @create 2021-01-13 14:07
 */
@Controller
public class PassportController {

    @Reference
    MemberService memberService;

    @RequestMapping("index")
    public String index(String ReturnUrl,Model model){
        model.addAttribute("ReturnUrl",ReturnUrl);
        return "index";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(Member member, HttpServletRequest request){
        //验证成功
        Member member1  = memberService.login(member);
        if(member1!=null){
            //制作token
            Map<String,Object> map = new HashMap<>();
            map.put("memberId",member1.getId());
            map.put("nickname",member1.getNickname());
            String ip = request.getRemoteAddr();
            // 从request中获取ip
            if(StringUtils.isBlank(ip)||ip.equals("0:0:0:0:0:0:0:1")){
                ip = "127.0.0.1";
            }
            String token = JwtUtil.encode("2020kmall075", map, ip );
            // 将token存入redis一份
            memberService.addUserToken(token,member1.getId());

            return token;
        }

        //验证失败
        return "fail";
    }
}
