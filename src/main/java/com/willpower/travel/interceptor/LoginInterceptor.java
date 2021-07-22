package com.willpower.travel.interceptor;

import com.willpower.travel.pojo.AdminUser;
import com.willpower.travel.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/18 20:50;
 * @Description: 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 目标方法之前执行执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //获取用户访问的地址
        String requestURI = request.getRequestURI();
        System.out.println("requestURI="+requestURI);
        if (requestURI.startsWith("/user_")){
            //前台用户访问地址
            User user= (User) session.getAttribute("user");
            if(user!=null){
                return true;
            }else{
                //前台登录入口
                response.getWriter().print("<script>location.href='/user_tologin';</script>");
                return false;
            }
        }else{
            AdminUser admin = (AdminUser) session.getAttribute("admin");
            //管理员登录
            if(admin!=null){
                return true;
            }else{
                //管理员跳转登录
                response.getWriter().print("<script>location.href='/login';</script>");
                return false;
            }
        }
    }
}
