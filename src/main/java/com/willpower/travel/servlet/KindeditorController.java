package com.willpower.travel.servlet;

import com.willpower.travel.utils.KindEditorResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/19 19:38;
 * @Description:
 */
@Controller
public class KindeditorController {
    //控制富文本插件图片上传
    @ResponseBody
    @RequestMapping("uploadimg_kindeditor/{address}")
    public KindEditorResult uploadimg(@PathVariable("address") String address, @RequestParam("file")MultipartFile multipartFile){
        if (!StringUtils.isEmpty(multipartFile)&&multipartFile.getSize()>0){
            //获取原始的文件名
            String oldname = multipartFile.getOriginalFilename();
            //获取文件后缀名
            String suffix = oldname.substring(oldname.lastIndexOf('.')+1);
            try {
                //文件上传的真实的路径
                String realpath = ResourceUtils.getURL("classpath:").getPath();
                String filePath="/"+address+"/"+new Date().getTime()+"."+suffix;

                //因为获取的绝对路径含有/ 去掉"/"
                if (realpath.startsWith("file:/usr")){
                    realpath = realpath.substring(5)+"static"+filePath;
                }else if (realpath.startsWith("file:/")){
                    realpath = realpath.substring(6)+"static"+filePath;
                }else {
                    realpath = realpath.substring(1)+"static"+filePath;
                }
                realpath = realpath.replace("%20", " ");

                File file = new File(realpath);
                multipartFile.transferTo(file);
                System.out.println("文件上传路径是："+realpath);
                return new KindEditorResult(0,filePath, null);
            } catch (Exception e) {
                e.printStackTrace();
                return KindEditorResult.ERROR;
            }
        }else return KindEditorResult.ERROR;
    }
}
