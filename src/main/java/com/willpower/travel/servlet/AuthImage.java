package com.willpower.travel.servlet;
import com.willpower.travel.utils.VerifyCodeUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @project: boxoffice;
 * @package: com.willpower.boxoffice.servlet;
 * @author: Administrator;
 * @date: 2021/5/12 10:12;
 * @Description: 生成验证码
 */
@Controller
public class AuthImage {
    @RequestMapping("createCode")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        System.out.println("系统生成的code："+verifyCode);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute("code");
        session.setAttribute("code", verifyCode);
        //生成图片
        int w = 100, h = 40;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }
}
