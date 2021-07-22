package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.service.ScenicSpotService;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/19 19:08;
 * @Description:
 */
@Controller
public class PortalScenicSpotController {
    @Autowired
    ScenicSpotService scenicSpotService;

    //前台获取数据展示 分页查询跳转页面
    @RequestMapping("portal_scenicSpot_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        IPage page = scenicSpotService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/travelSpot";
    }
    //前台旅游路线详情
    @GetMapping("portal_scenicSpot_toview/{id}")
    public String view(@PathVariable("id") String id,Model model){
        ScenicSpot scenicSpot = scenicSpotService.getById(id);
        model.addAttribute("entity", scenicSpot);
        return "portal/travelSpotView";
    }
}
