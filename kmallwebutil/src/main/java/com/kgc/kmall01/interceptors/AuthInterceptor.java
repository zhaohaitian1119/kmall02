package com.kgc.kmall01.interceptors;

import com.kgc.kmall01.annotations.LoginRequired;
import com.kgc.kmall01.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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




                boolean value = methodAnnotation.value();
                if(value){
                }
            }
        }
        return true;
    }
}
