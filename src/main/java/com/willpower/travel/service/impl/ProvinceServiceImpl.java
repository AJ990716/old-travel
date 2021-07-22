package com.willpower.travel.service.impl;

import com.willpower.travel.mapper.ProvinceMapper;
import com.willpower.travel.pojo.MainProvince;
import com.willpower.travel.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.service.impl;
 * @author: Administrator;
 * @date: 2021/5/24 21:00;
 * @Description:
 */
@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    ProvinceMapper provinceMapper;

    @Override
    public List<MainProvince> findAllData() {
        return provinceMapper.findAllData();
    }
}
