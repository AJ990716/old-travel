package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.willpower.travel.pojo.AdminUser;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.pojo.UserOrder;
import com.willpower.travel.service.AdminUserService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/17 17:50;
 * @Description: admin 后台登录模块
 */
@Controller
@Scope("singleton") //默认是单例模式
public class AdminUserController {
    @Autowired
    ObjectMapper mapper; //获取spring框架使用的jackson转json格式
    @Autowired
    AdminUserService adminUserService;
    @PostMapping("admin_login")
    public String login(String captcha, String remember, AdminUser adminUser, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        String code = (String) session.getAttribute("code");
        if (captcha.equalsIgnoreCase(code)){
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("user_name", adminUser.getUserName());
            wrapper.eq("password", adminUser.getPassword());
            wrapper.eq("delete_status", "0");
            AdminUser admin = adminUserService.getOne(wrapper);
            if (admin!=null){
                if (admin.getState()==1){
                    ServletContext context = request.getServletContext();
                    if (context.getAttribute("visit")==null)context.setAttribute("visit", 1);
                    else context.setAttribute("visit", (Integer)context.getAttribute("visit")+1);
                    session.setAttribute("admin", admin);
                    Cookie cookie = null;
                    Cookie[] cookies = request.getCookies();
                    for (Cookie c : cookies) {
                        if ("userInfo".equals(c.getName())){
                            cookie = c;
                            break;
                        }
                    }
                    if ("1".equals(remember)&&(cookie==null||"".equals(cookie.getValue()))){
                        try {
                            Map<String,String> map = new HashMap<>();
                            map.put("username", admin.getUserName());
                            map.put("password", admin.getPassword());
                            map.put("remember", "1");
                            cookie = new Cookie("userInfo", URLEncoder.encode(mapper.writeValueAsString(map), "UTF-8"));
                            cookie.setPath("/");
                            cookie.setMaxAge(60*60*12);
                            response.addCookie(cookie);
                        } catch (JsonProcessingException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }else if (remember==null&&cookie!=null&&!"".equals(cookie.getValue())){
                        cookie.setValue("");
                        cookie.setMaxAge(-1);
                        response.addCookie(cookie);
                    }
                    return "main";
                }else {
                    model.addAttribute("message", "该账户被禁用!");
                    return "login";
                }
            }else {
                model.addAttribute("message", "账户或密码错误!");
                return "login";
            }
        }else {
            model.addAttribute("message","验证码错误!");
            return "login";
        }
    }

    //退出登录
    @GetMapping("admin_logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    //系统用户 分页显示
    //分页查询跳转页面
    @RequestMapping("admin_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       @RequestParam(defaultValue = "") String title,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(title)){
            wrapper.like("user_name", title);
        }
        IPage page = adminUserService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);//数据回显
        return "admin/adminList";
    }

    //新增admin用户
    @ResponseBody
    @PostMapping("admin_add")
    public CommonResult add(AdminUser user){
        System.out.println(user);
        user.setAddTime(DataUtil.getNowTime());
        user.setDeleteStatus(0);
        user.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        return new CommonResult(200,"ok",adminUserService.save(user));
    }

    //带数据跳转详情界面
    @GetMapping("admin_toview/{id}")
    public String toview(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", adminUserService.getById(id));
        return "admin/adminView";
    }

    //带数据跳转到更新数据页面
    @GetMapping("admin_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id,Model model){
        System.out.println(id);
        model.addAttribute("entity", adminUserService.getById(id));
        return "admin/adminEdit";
    }
    //执行更新的方法   异步更新
    @ResponseBody
    @PostMapping("admin_update")
    public CommonResult update(AdminUser user, HttpSession session){
        try {
            //设置当前系统时间为更新事件
            user.setModifyTime(DataUtil.getNowTime());
            //设置修改人id
            user.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+user);
            return new CommonResult(200,"ok",adminUserService.updateById(user));
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"error");
        }
    }
    //使用异步删除
    @ResponseBody
    @GetMapping("admin_delete/{id}")
    public CommonResult delete(@PathVariable("id") String id){
        //TODO 后面补代码如果该景点被使用则不能被删除
        AdminUser user = adminUserService.getById(id);
        user.setDeleteStatus(1);
        return new CommonResult(200,"ok",adminUserService.updateById(user));
    }
}
