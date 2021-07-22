package com.willpower.travel.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.willpower.travel.service.PayService;
import com.willpower.travel.utils.WeixinHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/25 16:35;
 * @Description: 重点实现接口 调用微信Api的两大接口
 */
@Service
public class PayServiceImpl implements PayService {
    //注入需要的参数
    @Value("${appid}")
    private String appid;

    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerkey;

    @Value("${notifyurl}")
    private String notifyurl;

    //远程调用微信支付的统一下单接口   【订单  价格  产品信息】  返回url微信支付地址
    @Override
    public String callWeiXinCreateOrder(String order_id, String total_fee, String description) {
        //1、创建API所需的参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);      //公众号id
        param.put("mch_id", partner);   //商户号
        param.put("nonce_str", WXPayUtil.generateNonceStr());       // 随机字符串
        param.put("body", description);         //商品描述
        param.put("out_trade_no", order_id);    //商户订单号
        param.put("total_fee", total_fee);      //总金额（单位：分）
        param.put("spbill_create_ip", "127.0.0.1");     //IP
        param.put("notify_url", notifyurl);             //回调地址
        param.put("trade_type", "NATIVE");              //交易类型
        try {
            //2、使用微信SDK工具类，把Map格式的参数转为XML格式，因为微信支付接口 接收xml格式的数据
            String paramxml = WXPayUtil.generateSignedXml(param, partnerkey);
            WeixinHttpClient client = new WeixinHttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setHttps(true);
            client.setXmlParam(paramxml);
            client.post();

            //3、获取微信接口的返回结果(微信返回结果是xml格式的，为方便使用转成map)
            String content = client.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(content);
            return map.get("code_url");//返回微信支付的地址
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //检查订单是否支付 调用微信远程   查询订单状态  【订单id】  返回xml转换的map
    @Override
    public Map<String, String> queryPayStatus(String id) {
        Map param = new HashMap();
        param.put("appid", appid);          // 公众账号ID
        param.put("mch_id", partner);       // 商户号
        param.put("out_trade_no", id);      // 订单号
        param.put("nonce_str", WXPayUtil.generateNonceStr());   // 随机字符串
        try {
            String paramxml = WXPayUtil.generateSignedXml(param, partnerkey);
            WeixinHttpClient client = new WeixinHttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setHttps(true);
            client.setXmlParam(paramxml);
            client.post();

            //接口的返回结果
            String content = client.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(content);
            System.out.println(map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
