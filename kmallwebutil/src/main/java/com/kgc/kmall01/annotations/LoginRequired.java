package com.kgc.kmall01.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shkstart
 * @create 2021-01-12 15:36
 * 不需要拦截请求  不用添加此注解
需要拦截并登陆     value=true
需要拦截但不用登陆  value=false
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    boolean value() default true;
}
