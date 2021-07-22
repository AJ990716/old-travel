package com.willpower.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.willpower.travel.mapper.HotelMapper;
import com.willpower.travel.pojo.Hotel;
import com.willpower.travel.service.HotelService;
import org.springframework.stereotype.Service;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/21 20:20;
 * @Description:
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {
}
