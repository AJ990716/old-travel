package com.willpower.travel.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.willpower.travel.pojo.AdminUser;
import com.willpower.travel.service.AdminUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.test;
 * @author: Administrator;
 * @date: 2021/5/17 17:30;
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMybatisPlus {
    @Autowired
    AdminUserService adminUserService;
    @Test
    public void login(){
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("user_name", "root");
//        wrapper.eq("password", "123456");
//        AdminUser one = adminUserService.getOne(wrapper);
//        System.out.println(one==null?"登录失败":"登录成功");
    }
}
