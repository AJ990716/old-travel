package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.Hotel;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.service.HotelService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/22 14:52;
 * @Description:
 */
@Controller
public class PortalHotelAccommodation {
    @Autowired
    HotelService hotelService;

    //分页查询跳转页面
    @RequestMapping("portal_hotel_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");

        IPage page = hotelService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/hotelAccommodation";
    }


    //带数据跳转详情界面
    @GetMapping("portal_hotel_toview/{id}")
    public String toview(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", hotelService.getById(id));
        return "portal/hotelAccommodationView";
    }

    //带数据跳转 订单页面
    @GetMapping("portal_hotal_reserve/{id}")
    public String tohotalreserve(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", hotelService.getById(id));
        return "portal/reserve";
    }
}
