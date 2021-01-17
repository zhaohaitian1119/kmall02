package com.kgc.kmall01.interceptors;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall01.annotations.LoginRequired;
import com.kgc.kmall01.utils.CookieUtil;
import com.kgc.kmall01.utils.HttpclientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shkstart
 * @create 2021-01-13 14:00
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler.getClass().equals((org.springframework.web.method.HandlerMethod.class))){
            HandlerMethod handler1 = (HandlerMethod)handler;

            LoginRequired methodAnnotation = handler1.getMethodAnnotation(LoginRequired.class);
            // LoginRequired注解拦截
            if(methodAnnotation !=null){
                //获取token
                String token = null;

                String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
                if(StringUtils.isNotBlank(oldToken)){
                    token = oldToken;
                }

                String newToken = request.getParameter("token");
                if(StringUtils.isNotBlank(newToken)){
                    token = newToken;
                }

                String result = "fail";
                Map<String,String> map = new HashMap<>();
                if(StringUtils.isNotBlank(token)){
                    //从requst中回去ip
                    String ip = request.getRemoteAddr();
                    if(StringUtils.isBlank(ip)||ip.equals("0:0:0:0:0:0:0:1")){
                        ip = "127.0.0.1";
                    }

                    String doGet = HttpclientUtil.doGet("http://passport.kmall.com:8086/verify?token=" + token + "&currentIp=" + ip);
                    map = JSON.parseObject(doGet, Map.class);
                    result = map.get("status");
                }


                boolean value = methodAnnotation.value();
                if(value){
                    if(!result.equals("success")){
                        //重定向会passport登录
                        StringBuffer requestURL = request.getRequestURL();
                        response.sendRedirect("http://passport.kmall.com:8086/index?ReturnUrl="+requestURL);
                        return false;
                    }
                    // 需要将token携带的用户信息写入
                    request.setAttribute("memberId", map.get("memberId"));
                    request.setAttribute("nickname", map.get("nickname"));
                }else{
                    if(request.equals("success")){
                        // 需要将token携带的用户信息写入
                        request.setAttribute("memberId", map.get("memberId"));
                        request.setAttribute("nickname", map.get("nickname"));

                        //验证通过，覆盖cookie中的token
                        if(StringUtils.isNotBlank(token)){
                            CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
                        }
                    }
                }
            }else{
                return true;
            }
        }
        return true;
    }
}
