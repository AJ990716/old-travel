package com.willpower.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.willpower.travel.mapper.UserOrderMapper;
import com.willpower.travel.pojo.UserOrder;
import com.willpower.travel.service.UserOrderService;
import org.springframework.stereotype.Service;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/21 19:54;
 * @Description:
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
}
