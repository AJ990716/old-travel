package com.willpower.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.willpower.travel.mapper.UserMapper;
import com.willpower.travel.pojo.User;
import com.willpower.travel.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/18 19:43;
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
