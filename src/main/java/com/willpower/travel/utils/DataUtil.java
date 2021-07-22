package com.willpower.travel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.utils;
 * @author: Administrator;
 * @date: 2021/5/18 15:06;
 * @Description: 处理时间上传格式 与计算天数差
 */
public class DataUtil {
    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static long getTravelDay(String startTime,String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long time = 0;
        try {
            time = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time/(3600000*24)+1;
    }

    public static String getOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
