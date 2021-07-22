package com.willpower.travel.config;

import com.willpower.travel.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.config;
 * @author: Administrator;
 * @date: 2021/5/18 21:11;
 * @Description: 配置拦截器
 * WebMvcConfigurer
 */
@Configuration
public class MvcConfig  {
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration interceptor = registry.addInterceptor(new LoginInterceptor());
        interceptor.addPathPatterns("/**"); //所有路径都被拦截
        //添加不拦截路径
        interceptor.excludePathPatterns(
                "/",                      //前台首页不拦截
                "/index",
                "/createCode",            //验证码
                "/adminUser_login",       //后台用户登录
                "/login",                 //跳转后台登录地址
                "/css/**",
                "/hotel/**",
                "/insurance/**",
                "/js/**",
                "/layui/**",
                "/message/**",
                "/scenicSpot/**",
                "/strategy/**",
                "/travelRoute/**",
                "/images/**",
                "/car/**",
                "/favicon.ioc",
                "/portal_*",  //前台某些地址不拦截
                "/portal_*/**",  //前台某些地址不拦截
                "/user_tologin",  //前台前台跳转登录
                "/user_login",  //前台登录
                "/user_toregister",  //前台前台跳转注册
                "/user_register"  //前台注册
        );
    }
}
