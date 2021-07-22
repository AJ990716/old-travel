package com.willpower.travel.servlet;

import com.willpower.travel.pojo.UserOrder;
import com.willpower.travel.service.PayService;
import com.willpower.travel.service.UserOrderService;
import com.willpower.travel.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/25 9:32;
 * @Description:
 */
@Controller
public class PayController {

    @Autowired
    UserOrderService userOrderService;
    @Autowired
    PayService payService;

    /*
        01:跳转到支付界面，远程调用位置统一下单 接口，获取微信支付的二维码地址
     */
    @RequestMapping("user_topayOrder/{id}")
    public String topayOrder(@PathVariable("id") String id, Model model){
        //要支付的订单对象
        // id 调用微信支付的订单ID
        UserOrder order = userOrderService.getById(id);
        //订单金额(微信支付，订单金额以分为单位)
        double showFee = order.getFee();
        String fee = "1";
        //远程调用微信支付的统一下单接口   【订单  价格  产品信息】  返回url微信支付地址
        String payUrl = payService.callWeiXinCreateOrder(id, fee, order.getProductName());
        System.out.println("微信支付地址:"+payUrl);
        model.addAttribute("payUrl", payUrl);
        model.addAttribute("order_code", order.getOrderCode());
        model.addAttribute("fee", showFee);
        model.addAttribute("id", id);       //支付使用的订单号
        return "portal/pay";
    }
    @ResponseBody
    @RequestMapping("/user_queryPayStatus/{id}")
    public CommonResult user_queryPayStatus(@PathVariable("id") String id){
        CommonResult result = null;
        int x = 20;
        while (--x>0){
            Map<String, String> map = payService.queryPayStatus(id);
            if (map==null){
                result = new CommonResult(100101,"支付异常");
                break;
            }
            if (map.get("trade_state").equals("SUCCESS")){
                result = new CommonResult(200,"支付成功");
                //更新数据库支付状态
                UserOrder order = userOrderService.getById(id);
                order.setState(1L);//已付款
                userOrderService.updateById(order);
                break;
            }
            try {
                Thread.sleep(3000);//间隔3秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (x==0){
            result = new CommonResult(100102,"二维码超时");
            System.out.println("订单超时");
        }
        return result;
    }
    /**
     * 02-跳转支付成功页面
     * @return
     */
    @RequestMapping("/topaySuccess")
    public String topaySuccess() {
        return "portal/pay_success";
    }

    /**
     * 03-跳转支付失败页面
     *
     * @return
     */
    @RequestMapping("/topayFail")
    public String topayFail() {
        return "portal/pay_fail";
    }
}
