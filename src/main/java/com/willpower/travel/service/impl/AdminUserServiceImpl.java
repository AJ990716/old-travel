package com.willpower.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.willpower.travel.mapper.AdminUserMapper;
import com.willpower.travel.pojo.AdminUser;
import com.willpower.travel.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/17 16:59;
 * @Description:
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper,AdminUser> implements AdminUserService {
    @Resource
    AdminUserMapper adminUserMapper;
    @Override
    public AdminUser findAdminUser(AdminUser adminUser) {
        return adminUserMapper.findAdminUser(adminUser);
    }
}
