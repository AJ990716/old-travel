package com.willpower.travel.test;

import com.willpower.travel.Application;
import com.willpower.travel.mapper.AdminUserMapper;
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
 * @date: 2021/5/17 17:02;
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestMybatis {
    @Autowired
    AdminUserService adminUserService;
    @Test
    public void login(){
//        AdminUser adminUser = new AdminUser();
//        adminUser.setUserName("root");
//        adminUser.setPassword("123456");
//        AdminUser find = adminUserService.findAdminUser(adminUser);
//        System.out.println(find==null?"登录失败":"登录成功");
    }
}
