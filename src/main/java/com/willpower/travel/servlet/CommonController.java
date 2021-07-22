package com.willpower.travel.servlet;

import com.willpower.travel.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/17 18:56;
 * @Description: 主要用于页面的跳转
 */
@Controller
public class CommonController {
    //跳转前台页面
    @GetMapping("index")
    public String toindex(){
        return "portal/index";
    }
    //跳转后台登录页面
    @GetMapping({"login","/"})
    public String toLogin(){
        return "login";
    }

    //获取登录的验证码
    @PostMapping("code")
    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        response.getWriter().print(session.getAttribute("code"));
    }
    //获取登录次数
    @GetMapping("visit")
    public void getVisit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ServletContext context = request.getServletContext();
        response.getWriter().print(context.getAttribute("visit"));
    }
    //跳转添加页面
    @GetMapping("travelRoute_toadd")
    public String toadd(){
        return "travelRoute/travelRouteAdd";
    }

    //跳转前台页面登录
    @GetMapping("user_tologin")
    public String tologin(){
        return "portal/login";
    }

    //跳转注册页面
    @GetMapping("user_toregister")
    public String toregister(){
        return "portal/register";
    }

    //跳转景点新增页面
    @GetMapping("scenicSpot_toadd")
    public String toAdd(){
        return "scenicSpot/scenicSpotAdd";
    }

    //前端跳转个人界面
    @RequestMapping("user_topersonInfo")
    public String topersonInfo(HttpSession session, Model model){
        User user= (User) session.getAttribute("user");
        model.addAttribute("entity",user);
        return "portal/personalIntro";
    }

    //跳转到更新密码页面
    @GetMapping("user_tochangePassword")
    public String tochangePassword(){
        return "portal/changePassword";
    }

    //跳转酒店新增管理
    @GetMapping("hotel_toadd")
    public String tohoteladd(){
        return "hotel/hotelAdd";
    }

    //跳转系统用户新增管理
    @GetMapping("admin_toadd")
    public String toadminadd(){
        return "admin/adminAdd";
    }

    //跳转普通用户新增管理
    @GetMapping("user_toadd")
    public String touseradd(){
        return "user/userAdd";
    }

    //跳转注意事项
    @GetMapping("portal_toattention")
    public String user_toattention(){
        return "portal/attention";
    }

    //跳转car添加页面
    @GetMapping("car_toadd")
    public String tocaradd(){
        return "car/carAdd";
    }

    //跳转insurance添加页面
    @GetMapping("insurance_toadd")
    public String toinsuranceadd(){
        return "insurance/insuranceAdd";
    }

    //跳转strategy添加页面
    @GetMapping("strategy_toadd")
    public String tostrategyadd(){
        return "strategy/strategyAdd";
    }
}
