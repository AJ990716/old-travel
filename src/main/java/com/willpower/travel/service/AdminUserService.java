package com.willpower.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.willpower.travel.pojo.AdminUser;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service;
 * @author: Administrator;
 * @date: 2021/5/17 16:59;
 * @Description:
 */
public interface AdminUserService extends IService<AdminUser> {
    AdminUser findAdminUser(AdminUser adminUser);
}
