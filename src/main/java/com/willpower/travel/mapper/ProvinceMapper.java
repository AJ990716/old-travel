package com.willpower.travel.mapper;

import com.willpower.travel.pojo.MainProvince;

import java.util.List;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.mapper;
 * @author: Administrator;
 * @date: 2021/5/24 20:54;
 * @Description:
 */
public interface ProvinceMapper {
    List<MainProvince> findAllData();
}
