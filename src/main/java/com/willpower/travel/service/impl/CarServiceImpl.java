package com.willpower.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.willpower.travel.mapper.CarMapper;
import com.willpower.travel.pojo.Car;
import com.willpower.travel.service.CarService;
import org.springframework.stereotype.Service;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/22 16:27;
 * @Description:
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {
}
