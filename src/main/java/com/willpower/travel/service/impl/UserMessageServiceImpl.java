package com.willpower.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.willpower.travel.mapper.UserMessageMapper;
import com.willpower.travel.pojo.UserMessage;
import com.willpower.travel.service.UserMessageService;
import org.springframework.stereotype.Service;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/20 20:48;
 * @Description:
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {
}
