package com.willpower.travel.service;

import java.util.Map;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service;
 * @author: Administrator;
 * @date: 2021/5/25 16:32;
 * @Description:
 */
public interface PayService {
    //远程调用微信支付的统一下单接口   【订单  价格  产品信息】  返回url微信支付地址
    String callWeiXinCreateOrder(String order_id, String total_fee,String description);

    //检查订单是否支付 调用微信远程   查询订单状态  【订单id】  返回xml转换的map
    Map<String, String> queryPayStatus(String id);
}
