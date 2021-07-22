package com.willpower.travel.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.utils;
 * @author: Administrator;
 * @date: 2021/5/18 12:47;
 * @Description: 异步json数据的返回格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {

    private Integer code;
    private String message;
    private Object data;

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
