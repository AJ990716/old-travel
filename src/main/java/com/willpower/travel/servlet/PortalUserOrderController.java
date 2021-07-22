package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.*;
import com.willpower.travel.service.*;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpSession;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/21 19:54;
 * @Description: 订单模块
 */
@Controller
public class PortalUserOrderController {
    @Autowired
    UserOrderService orderService;
    //旅游路线
    @Autowired
    TravelRouteService travelRouteService;
    //景点
    @Autowired
    ScenicSpotService scenicSpotService;
    //酒店
    @Autowired
    HotelService hotelService;
    //车票
    @Autowired
    CarService carService;
    //保险
    @Autowired
    InsuranceService insuranceService;

    @RequestMapping("user_createOrder")
    public String user_createOrder(String id, int product_type, HttpSession session, Model model){
        String viewName = "";
        try {

            User user = (User) session.getAttribute("user");
            UserOrder userOrder = new UserOrder();

            userOrder.setAddUserId(user.getId());//设置添加者用户id
            userOrder.setAddTime(DataUtil.getNowTime());//设置添加的时间
            userOrder.setDeleteStatus(0);//设置删除的状态
            userOrder.setUserId(user.getId());
            userOrder.setUserName(user.getUserName());
            userOrder.setProductId(id);
            userOrder.setProductType(product_type);//设置订单类型
            userOrder.setState(0);//设置订单状态
            userOrder.setOrderCode(DataUtil.getOrderId());//创建订单号
            userOrder.setOrderTime(DataUtil.getNowTime());
            userOrder.setLinkTel(user.getLinkTel());
            userOrder.setIcCode(user.getIcCode());
            if (product_type == 0) {
                //旅游路线
                TravelRoute travelRoute = travelRouteService.getById(id);
                userOrder.setProductName(travelRoute.getTitle());//设置产品名字
                userOrder.setFee(travelRoute.getPrice());//设置价格
                userOrder.setSetoffTime(travelRoute.getStartTime());//设置开放时间
                userOrder.setImgUrl(travelRoute.getImgUrl());//设置图片
                model.addAttribute("entity",travelRoute);//发送给前端展示
                viewName="portal/travelRouteView";//返回的页面
            } else if (product_type == 1) {
                //旅游景点
                ScenicSpot scenicSpot = scenicSpotService.getById(id);
                userOrder.setProductName(scenicSpot.getSpotName());//设置产品名字
                userOrder.setFee(scenicSpot.getTicketsMessage());//设置价格
                userOrder.setSetoffTime(scenicSpot.getOpenTime());//设置开放时间
                userOrder.setImgUrl(scenicSpot.getImgUrl());//设置图片
                model.addAttribute("entity",scenicSpot);//发送给前端展示
                viewName="portal/travelSpotView";//返回的页面
            } else if (product_type == 3) {
                //车票信息
                Car car = carService.getById(id);
                userOrder.setProductName(car.getTitle());//设置产品名字
                userOrder.setFee(car.getPrice());//设置价格
                userOrder.setSetoffTime(car.getStartDateAndTime());//设置开放时间
                userOrder.setImgUrl(car.getImgUrl());//设置图片
                model.addAttribute("entity",car);//发送给前端展示
                viewName="portal/carView";//返回的页面
            } else if (product_type == 4) {
                //保险信息
                Insurance insurance = insuranceService.getById(id);
                userOrder.setProductName(insurance.getTitle());//设置产品名字
                userOrder.setFee(insurance.getPrice());//设置价格
                userOrder.setImgUrl(insurance.getImgUrl());//设置图片
                model.addAttribute("entity",insurance);//发送给前端展示
                viewName="portal/insuranceView";//返回的页面
            }
            orderService.save(userOrder);//添加数据
            model.addAttribute("msg","预定成功，请前往会员中心-我的订单查看订单");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "预定异常");
        }
        return viewName;
    }

    //添加酒店的订单
    @RequestMapping("user_createHotelOrder")
    public String createHotelOrder(Hotel hotel,Long peopleCount,String setoffTime,String requirement,HttpSession session,Model model){
        try {
            User user = (User) session.getAttribute("user");
            UserOrder userOrder = new UserOrder();

            userOrder.setAddUserId(user.getId());//设置添加者用户id
            userOrder.setAddTime(DataUtil.getNowTime());//设置添加的时间
            userOrder.setDeleteStatus(0);//设置删除的状态
            userOrder.setUserId(user.getId());
            userOrder.setUserName(user.getUserName());
            userOrder.setProductId(hotel.getId());
            userOrder.setProductType(2);//设置订单类型
            userOrder.setState(0);//设置订单状态
            userOrder.setOrderCode(DataUtil.getOrderId());//创建订单号
            userOrder.setOrderTime(DataUtil.getNowTime());
            userOrder.setLinkTel(user.getLinkTel());
            userOrder.setIcCode(user.getIcCode());

            Hotel h  = hotelService.getById(hotel.getId());
            userOrder.setProductName(h.getHotelName());//设置产品名字
            userOrder.setFee(h.getPrice());//设置价格
            userOrder.setImgUrl(h.getImgUrl());//设置图片
            userOrder.setPeopleCount(peopleCount);//设置人数
            userOrder.setSetoffTime(setoffTime);//使用时间
            userOrder.setRequirement(requirement);//其它要求

            orderService.save(userOrder);//添加数据
            model.addAttribute("msg","预定成功，请前往会员中心-我的订单查看订单");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "预定异常");
        }
        model.addAttribute("entity", hotelService.getById(hotel.getId()));
        return "portal/hotelAccommodationView";
    }
    //分页查看我的订单
    @RequestMapping("user_myorderlist")
    public String myorderlist(@RequestParam(defaultValue = "1") Long pageNumber,
                              @RequestParam(defaultValue = "5") Long pageSize,
                              HttpSession session, Model model){
        //当前用户信息
        User user= (User) session.getAttribute("user");
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("DELETE_STATUS",0);
        wrapper.eq("USER_ID",user.getId());
        wrapper.orderByDesc("ADD_TIME");
        IPage page = orderService.page(new Page<UserOrder>(pageNumber, pageSize), wrapper);

        //封装工具类
        PagerHelper<UserOrder> pagerHelper=new PagerHelper<UserOrder>(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/myOrder";
    }

    //用户取消订单
    @RequestMapping("user_cancelOrder/{id}/{pageNumber}")
    public String user_cancelOrder(@PathVariable("id") String id,
                                   @PathVariable("pageNumber") Long pageNumber,
                                   HttpSession session, Model model){
        User user= (User) session.getAttribute("user");
        UserOrder userOrder = orderService.getById(id);
        userOrder.setState(2);//取消订单
        orderService.updateById(userOrder);

        //当前用户信息
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("DELETE_STATUS",0);
        wrapper.eq("USER_ID",user.getId());
        wrapper.orderByDesc("ADD_TIME");
        IPage page = orderService.page(new Page<UserOrder>(pageNumber, 7), wrapper);

        //封装工具类
        PagerHelper<UserOrder> pagerHelper=new PagerHelper<UserOrder>(pageNumber,7L,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        return "portal/myOrder";
    }
}
