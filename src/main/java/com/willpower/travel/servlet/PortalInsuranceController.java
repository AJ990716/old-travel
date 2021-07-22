package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.service.InsuranceService;
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
 * @date: 2021/5/22 19:27;
 * @Description:
 */
@Controller
public class PortalInsuranceController {
    @Autowired
    InsuranceService insuranceService;
    //分页查询跳转页面
    @RequestMapping("portal_insurance_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       @RequestParam(defaultValue = "") String title,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(title)){
            wrapper.like("title", title);
        }
        IPage page = insuranceService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);//数据回显
        return "portal/insurance";
    }
    //带数据跳转详情界面
    @GetMapping("portal_insurance_toview/{id}")
    public String toview(@PathVariable("id") String id, Model model){
        model.addAttribute("entity", insuranceService.getById(id));
        return "portal/insuranceView";
    }
}
