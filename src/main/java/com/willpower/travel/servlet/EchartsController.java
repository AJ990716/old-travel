package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.willpower.travel.pojo.MainProvince;
import com.willpower.travel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/24 14:40;
 * @Description:
 */
@Controller
public class EchartsController {
    //路线
    @Autowired
    TravelRouteService routeService;
    //用户
    @Autowired
    UserService userService;
    //景点
    @Autowired
    ScenicSpotService scenicSpotService;
    //酒店
    @Autowired
    HotelService hotelService;
    //攻略
    @Autowired
    StrategyService strategyService;
    //车票
    @Autowired
    CarService carService;
    //保险
    @Autowired
    InsuranceService insuranceService;
    //订单
    @Autowired
    UserOrderService orderService;
    //省份
    @Autowired
    ProvinceService provinceService;

    //路线
    @RequestMapping("/trave_route_data")
    public String traveRouteData(Model model) throws JsonProcessingException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = routeService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "未发布");break;
                case 1: map.put("name", "发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);
        model.addAttribute("datas", json);
        return "data/trave_route_data";
    }
    //用户
    @RequestMapping("/user_data")
    public String userData(Model model) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = userService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "测试");break;
                case 1: map.put("name", "注册");break;
                case 2: map.put("name", "注销");break;
            }
        }
        System.out.println(list);
        model.addAttribute("datas", list);
        return "data/user_data";
    }
    //景点
    @RequestMapping("/scenic_spot_data")
    public String scenicSpotData(Model model) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = scenicSpotService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "待发布");break;
                case 1: map.put("name", "已发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        System.out.println(list);
        model.addAttribute("datas", list);
        return "data/scenic_spot_data";
    }
    //酒店
    @RequestMapping("/hotel_data")
    public String hotelData(Model model) throws JsonProcessingException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = hotelService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "待发布");break;
                case 1: map.put("name", "发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);
        System.out.println(json);
        model.addAttribute("datas", json);
        return "data/hotel_data";
    }
    //攻略
    @RequestMapping("/strategy_data")
    public String strategyData(Model model) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = strategyService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "待发布");break;
                case 1: map.put("name", "已发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        System.out.println(list);
        model.addAttribute("datas", list);
        return "data/strategy_data";
    }
    //车票
    @RequestMapping("/car_data")
    public String carData(Model model) throws JsonProcessingException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = carService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "未发布");break;
                case 1: map.put("name", "发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);
        model.addAttribute("datas", json);
        return "data/car_data";
    }
    //保险
    @RequestMapping("insurance_data")
    public String insuranceData(Model model) throws JsonProcessingException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = insuranceService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "未发布");break;
                case 1: map.put("name", "发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);
        model.addAttribute("datas", json);
        return "data/insurance_data";
    }
    //订单
    @RequestMapping("order_data")
    public String orderData(Model model) throws JsonProcessingException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.select("state,count(id) value").groupBy("state");
        wrapper.orderByAsc("state");
        List<Map<String, Object>> list = orderService.listMaps(wrapper);
        for (Map<String, Object> map : list) {
            Integer state = (Integer)map.get("state");
            switch (state){
                case 0: map.put("name", "未发布");break;
                case 1: map.put("name", "发布");break;
                case 2: map.put("name", "注销");break;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);
        model.addAttribute("datas", json);
        return "data/order_data";
    }

    /**
     * 中国地图数据分析
     */
    @RequestMapping("/main")
    public String tomain(Model model) throws Exception{
        List<MainProvince> datas = provinceService.findAllData();
        ObjectMapper objectMapper=new ObjectMapper();
        String json = objectMapper.writeValueAsString(datas);
        System.out.println(datas);

        model.addAttribute("datas",json);

        return "data/main";
    }
}
