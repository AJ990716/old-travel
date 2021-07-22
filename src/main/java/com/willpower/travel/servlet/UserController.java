package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.AdminUser;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.pojo.User;
import com.willpower.travel.service.UserService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/18 19:45;
 * @Description:
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    //会员登录
    @PostMapping("user_login")
    public String login(User user, String userCode, HttpSession session, Model model){
        String code = (String) session.getAttribute("code");
        if (code.equals(userCode)){
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("user_name", user.getUserName());
            wrapper.eq("password", user.getPassword());
            wrapper.eq("delete_status", 0);
            User login = userService.getOne(wrapper);
            if (login!=null){
                if (login.getState()==1){
                    System.out.println("登陆成功："+login);
                    session.setAttribute("user", login);
                    //跳转个人详情页
                    session.setAttribute("userName", login.getUserName());
                    model.addAttribute("entity",login);
                    return "portal/personalIntro";
                }else {
                    model.addAttribute("message","该用户被禁用!");
                    return "portal/login";
                }
            }else {
                model.addAttribute("message","账户或密码错误!");
                return "portal/login";
            }
        }else {
            model.addAttribute("message","验证码错误!");
            return "portal/login";
        }
    }

    //会员注册
    @PostMapping("user_register")
    public String register(User user,Model model){
        user.setDeleteStatus(0);
        user.setAddTime(DataUtil.getNowTime());
        user.setState(1L);
        boolean save = userService.save(user);
        if (save)return "portal/login";
        else {
            model.addAttribute("message", "注册失败!");
            return "portal/register";
        }
    }

    //用户退出
    @GetMapping("user_logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "portal/login";
    }

    //用户修改密码页面
    @PostMapping("/user_changePassword")
    @ResponseBody
    public CommonResult changePassword(String password, String newPassword, String checkPassword, HttpSession session){
        User user= (User) session.getAttribute("user");
        if(StringUtils.isEmpty(newPassword)||StringUtils.isEmpty(checkPassword)){
            return new CommonResult(500,"要修改的密码不能为空!");
        }else if(!newPassword.equals(checkPassword)){
            return new CommonResult(500,"两次输入的密码不一致!");
        }else if(!user.getPassword().equals(password)){
            return new CommonResult(500,"原密码输入错误!");
        }else if(password.equals(newPassword)){
            return new CommonResult(500,"原密码不能和新密码相同!");
        }else{
            user.setPassword(newPassword);
            boolean b = userService.updateById(user); //更新数据库
            if(b){
                session.invalidate();
                return new CommonResult(200,"密码修改成功!");
            }else{
                return new CommonResult(500,"密码更新异常");
            }
        }
    }
    //用户信息更新
    @PostMapping("user_updateInfo")
    public String user_updateInfo(User user,Model model){
        try {
            user.setState(1L);
            boolean b = userService.updateById(user);
            model.addAttribute("message","个人信息更新成功");
        } catch (Exception e) {
            model.addAttribute("message","个人信息更新异常");
        }
        User u = userService.getById(user.getId());
        model.addAttribute("entity",u);
        return "portal/personalIntro";
    }

    //跳转 分页用户管理界面
    //分页查询跳转页面
    @RequestMapping("user_list")
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
        IPage page = userService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);//数据回显
        return "user/allUsers";
    }

    //新增用户
    @ResponseBody
    @PostMapping("user_add")
    public CommonResult add(User user){
        System.out.println(user);
        user.setAddTime(DataUtil.getNowTime());
        user.setDeleteStatus(0);
        user.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        return new CommonResult(200,"ok",userService.save(user));
    }

    //带数据跳转详情界面
    @GetMapping("user_toview/{id}")
    public String toview(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", userService.getById(id));
        return "user/userView";
    }

    //带数据跳转到更新数据页面
    @GetMapping("user_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id,Model model){
        System.out.println(id);
        model.addAttribute("entity", userService.getById(id));
        return "user/userEdit";
    }
    //执行更新的方法   异步更新
    @ResponseBody
    @PostMapping("user_update")
    public CommonResult update(User user, HttpSession session){
        try {
            //设置当前系统时间为更新事件
            user.setModifyTime(DataUtil.getNowTime());
            //设置修改人id
            user.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+user);
            return new CommonResult(200,"ok",userService.updateById(user));
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"error");
        }
    }
    //使用异步删除
    @ResponseBody
    @GetMapping("user_delete/{id}")
    public CommonResult delete(@PathVariable("id") String id){
        //TODO 后面补代码如果该景点被使用则不能被删除
        User user = userService.getById(id);
        user.setDeleteStatus(1);
        return new CommonResult(200,"ok",userService.updateById(user));
    }
}
