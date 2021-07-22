package com.willpower.travel.servlet;

import com.willpower.travel.utils.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/18 12:49;
 * @Description: 全局异常处理 出现异常 同一返回   500 error
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public CommonResult handleArithmeticException(Exception ex) {
        ex.printStackTrace();
        CommonResult result = new CommonResult(500, "error");
        return result;
    }
}
