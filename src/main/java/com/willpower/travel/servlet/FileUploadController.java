package com.willpower.travel.servlet;

import com.willpower.travel.utils.CommonResult;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/18 17:41;
 * @Description:
 */
@Controller
public class FileUploadController {
    /**
     * 通用的异步文件上传方式
     */
    @RequestMapping("uploadImg/{address}")
    @ResponseBody
    public CommonResult upload(@PathVariable("address") String address, @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        if (!StringUtils.isEmpty(multipartFile)&&multipartFile.getSize()>0){
            //获取原始的文件名
            String oldName = multipartFile.getOriginalFilename();
            //获取文件名的后缀
            String sub = oldName.substring(oldName.lastIndexOf('.') + 1);
            try {

                /*
                System.out.println("项目路径(RequestURI):"+request.getRequestURI());
                System.out.println("项目路径(ServletPath):"+request.getServletPath());
                System.out.println("项目路径(ContextPath):"+request.getContextPath());
                System.out.println("classpath路径(thread/Resource):"+Thread.currentThread().getContextClassLoader().getResource("").getPath());
                System.out.println("项目路径(getResource):"+FileUploadController.class.getResource("").toString());
                System.out.println("项目路径(ServletContext):"+request.getSession().getServletContext().getRealPath(""));
                System.out.println("项目路径(classpath):"+ResourceUtils.getURL("classpath:").getPath());
                */

                //文件上传的真实路径     因为这是springboot 框架所以 普通的类上下文加载器无法获取正确的路径
                String path = ResourceUtils.getURL("classpath:").getPath();
                String filePath = "/"+address+"/"+new Date().getTime()+"."+sub;

                //获取文件流
                InputStream stream = getClass().getClassLoader().getResourceAsStream(filePath);
                System.out.println("this is："+stream);

                //因为获取的绝对路径含有/ 去掉"/"
                if (path.startsWith("file:/usr")){
                    path = path.substring(5)+"static"+filePath;
                }else if (path.startsWith("file:/")){
                    path = path.substring(6)+"static"+filePath;
                }else {
                    path = path.substring(1)+"static"+filePath;
                }
                path = path.replace("%20", " ");
                System.out.println("现目前的上传的地址："+path);
                System.out.println("测试的地址："+"E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\oldtravle-0.0.1-SNAPSHOT.jar!\\BOOT-INF\\classes!\\static\\travelRoute\\1621392760036.jpg");
                String testpath = "E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\oldtravle-0.0.1-SNAPSHOT.jar!\\BOOT-INF\\classes!\\static\\travelRoute\\1621392760036.jpg";
                File file = new File(path);
                System.out.println("jar中文件读不了？？？？"+file.exists());
                System.out.println("jar是绝对地址？？？？"+file.isAbsolute());
                System.out.println("jar中文件位置？？？？"+file.getPath());
                //StandardMultipartHttpServletRequest$StandardMultipartFile
                System.out.println(multipartFile);
                multipartFile.transferTo(file);
                System.out.println("文件上传的路径为："+path);
                return new CommonResult(200,"ok",filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return new CommonResult(500,"file upload error");
            }
        }else {
            return new CommonResult(1024,"file is null");
        }
    }
}