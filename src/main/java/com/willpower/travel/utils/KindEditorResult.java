package com.willpower.travel.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.pojo;
 * @author: Administrator;
 * @date: 2021/5/19 19:39;
 * @Description: 富文本返回格式 json对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KindEditorResult {
    private int error;
    private String url;
    private String message;
    public static final KindEditorResult ERROR = new KindEditorResult(1,null,"error");
}
