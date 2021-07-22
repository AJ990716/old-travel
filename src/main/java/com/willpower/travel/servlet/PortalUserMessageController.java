package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.User;
import com.willpower.travel.pojo.UserMessage;
import com.willpower.travel.service.UserMessageService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpSession;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/20 20:58;
 * @Description:
 */
@Controller
public class PortalUserMessageController {
    @Autowired
    UserMessageService messageService;

    //带数据跳转留言界面
    @GetMapping("user_tomyMessage")
    public String tomyMessage(Model model, HttpSession session){
        User user= (User) session.getAttribute("user");
        System.out.println(user);
        //查询当前人的留言条数
        QueryWrapper wrapper=new QueryWrapper<>();
        wrapper.eq("DELETE_STATUS",0);
        wrapper.eq("USER_ID",user.getId());
        int count = messageService.count(wrapper);

        model.addAttribute("messageCount",count);
        return "portal/myMessage";
    }
    //后台会员留言的接口
    @ResponseBody
    @RequestMapping("user_leaveMessage")
    public CommonResult leaveMessage(UserMessage message,HttpSession session){
        User user = (User) session.getAttribute("user");
        message.setAddTime(DataUtil.getNowTime());
        message.setAddUserId(user.getId());
        message.setDeleteStatus(0);
        message.setState(0);
        message.setUserId(user.getId());
        message.setUserName(user.getUserName());
        message.setName(user.getName());
        return new CommonResult(200,"请求成功",messageService.save(message));
    }

    //带信息跳转我的留言信息 分页
    @RequestMapping("/user_messageList")
    public String tomessagelist(@RequestParam(defaultValue = "1") Long pageNumber,
                                @RequestParam(defaultValue = "5") Long pageSize,
                                HttpSession session,Model model){
        //获取当前对象信息
        User user = (User)session.getAttribute("user");

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.eq("user_id", user.getId());
        wrapper.orderByDesc("add_time");
        IPage page = messageService.page(new Page<>(pageNumber,pageSize),wrapper);

        PagerHelper<UserMessage> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        return "portal/messageList";
    }
    //带数据跳转查询页面
    @RequestMapping("user_messageView/{id}")
    public String messageView(@PathVariable("id") String id, Model model){
        UserMessage userMessage = messageService.getById(id);
        model.addAttribute("userMessage",userMessage);
        return "portal/messageView";
    }
}
