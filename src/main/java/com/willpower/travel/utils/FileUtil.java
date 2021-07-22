package com.willpower.travel.utils;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.utils;
 * @author: Administrator;
 * @date: 2021/5/19 11:21;
 * @Description:
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * File工具类
 * @author CY
 *
 */
public class FileUtil {

    public static final int BYTESIZE = 1024;                                        //每次读取的大小 1KB

    /**
     * 将文件流保存在项目WEB-INF/temp目录下，并且返回这个文件；
     * @param is              待转化的文件流
     * @param fileName        临时文件名
     * @return
     * @throws IOException
     */
    public static File saveTempFile(InputStream is, String fileName){
        String path = "";
        try {
            path = URLDecoder.decode(FileUtil.class.getClassLoader().getResource("../temp").getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(path);
        ///E:/workspace/workspace for j2ee/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MyTest/WEB-INF/temp/

        File temp = new File(path + fileName);

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try{
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream(temp));                            //把文件流转为文件，保存在临时目录
            int len = 0;
            byte[] buf = new byte[10*BYTESIZE];                                                    //缓冲区
            while((len=bis.read(buf)) != -1){
                bos.write(buf, 0, len);
            }
            bos.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(bos!=null) bos.close();
                if(bis!=null) bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    /**
     * 删除文件  用来删除临时文件
     * @param file
     */
    public static void deleteTempFile(File file){
        file.delete();
    }

}