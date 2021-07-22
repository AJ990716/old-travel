package com.willpower.travel.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.util;
 * @author: Administrator;
 * @date: 2021/5/18 12:42;
 * @Description: 对分页对象的普通属性封装 面向对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagerHelper<T> {
    private Long pageNumber; //当前页码
    private Long pageSize; //页面大小
    private Long pageCount; //总页数
    private Long totalCount; //总条数
    private List<T> records;
}
