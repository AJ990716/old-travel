package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.UserMessage;
import com.willpower.travel.service.UserMessageService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/21 19:07;
 * @Description:
 */
@Controller
public class UserMessageController {
    @Autowired
    UserMessageService messageService;

    //留言分页显示
    @RequestMapping("message_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       @RequestParam(defaultValue = "") String userName,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(userName)){
            wrapper.like("user_name", userName);
        }
        IPage page = messageService.page(new Page<>(pageNumber,pageSize),wrapper);
        //封装工具类
        PagerHelper<UserMessage> pagerHelper = new PagerHelper<>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "message/messageList";
    }
    //带数据 跳转用户详情页面
    @RequestMapping("message_View/{id}")
    public String message_View(@PathVariable("id") String id, Model model){
        UserMessage userMessage = messageService.getById(id);
        userMessage.setState(1);//留言已读
        messageService.updateById(userMessage);
        model.addAttribute("entity",userMessage);
        return "message/messageView";
    }
    //带数据跳转回复界面
    @RequestMapping("message_replyView/{id}")
    public String message_replyView(@PathVariable("id") String id,Model model){
        UserMessage userMessage = messageService.getById(id);
        userMessage.setState(1);//留言已读
        messageService.updateById(userMessage);
        model.addAttribute("entity",userMessage);
        return "message/messageReply";
    }
    //留言 异步回复
    @ResponseBody
    @RequestMapping("replyMessage")
    public CommonResult replyMessage(String id,String replyContent){
        UserMessage userMessage = messageService.getById(id);
        userMessage.setReplyContent(replyContent);
        return new CommonResult(200,"ok", messageService.updateById(userMessage));
    }
    //留言 异步删除
    @ResponseBody
    @RequestMapping("message_delete/{id}")
    public CommonResult message_delete(@PathVariable("id") String id){
        UserMessage message = messageService.getById(id);
        message.setDeleteStatus(1);
        return new CommonResult(200, "ok", messageService.updateById(message));
    }
}
