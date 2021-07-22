package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.TravelRoute;
import com.willpower.travel.service.TravelRouteService;
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
 * @date: 2021/5/19 17:03;
 * @Description:
 */
@Controller
public class PortalTravelRoutController {
    @Autowired
    TravelRouteService routeService;

    //分页查询
    @RequestMapping("portal_travelRoute_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        IPage page = routeService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper", pagerHelper);
        return "portal/travelRoute";
    }
    //路线详细
    @GetMapping("portal_travelRoute_toview/{id}")
    public String view(@PathVariable("id") String id,Model model){
        TravelRoute travelRoute = routeService.getById(id);
        model.addAttribute("entity", travelRoute);
        return "portal/travelRouteView";
    }
}
