package com.willpower.travel.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.TravelRoute;
import com.willpower.travel.service.TravelRouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.test;
 * @author: Administrator;
 * @date: 2021/5/18 11:50;
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPageConfig {
    @Autowired
    TravelRouteService routeService;

    @Test
    public void test(){
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("delete_status", 1);
//        IPage page = routeService.page(new Page<TravelRoute>(2,5),wrapper);
//
//        List<TravelRoute> records = page.getRecords();
//        for (TravelRoute record : records) {
//            System.out.println(record);
//        }
//        System.out.println("总页数是："+page.getPages());
//        System.out.println("总条数是："+page.getTotal());
    }
}
