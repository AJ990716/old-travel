package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.pojo.User;
import com.willpower.travel.service.StrategyService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/22 14:36;
 * @Description:
 */
@Controller
public class PortalStrategyController {
    @Autowired
    StrategyService strategyService;

    //分页 跳转旅游攻略
    @RequestMapping("portal_strategy_list")
    public String user_tostrategy(@RequestParam(defaultValue = "1") Long pageNumber,
                                  @RequestParam(defaultValue = "5") Long pageSize,
                                  @RequestParam(defaultValue = "") String title,
                                  Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(title)){
            wrapper.like("user_name", title);
        }
        IPage page = strategyService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);//数据回显
        return "portal/strategy";
    }
    //带数据跳转详情界面
    @GetMapping("portal_strategy_toview/{id}")
    public String toview(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", strategyService.getById(id));
        return "portal/strategyView";
    }
}
