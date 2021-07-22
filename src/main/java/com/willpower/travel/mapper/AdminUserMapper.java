package com.willpower.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.willpower.travel.pojo.AdminUser;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.mapper;
 * @author: Administrator;
 * @date: 2021/5/17 16:31;
 * @Description:
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {
    AdminUser findAdminUser(AdminUser adminUser);
}
