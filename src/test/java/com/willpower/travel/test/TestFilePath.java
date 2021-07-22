package com.willpower.travel.test;

import com.willpower.travel.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.test;
 * @author: Administrator;
 * @date: 2021/5/18 18:55;
 * @Description:
 */
public class TestFilePath {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "E:\\IntelliJ%20IDEA\\IntelliJ%20IDEA4\\SpringBoot\\oldtravle\\target\\classes\\static\\travelRoute\\1621335265813.jpg";
        File file = new File(path);
        System.out.println(file.exists());

        //模拟得到一个文件流
        //InputStream fis = new FileInputStream(new File("E:\\MA5800系列-pic.zip"));

        //保存在WEB-INF/temp目录下
        //File temp = FileUtil.saveTempFile(fis, "MA5800.zip");
        //System.out.println(temp.getAbsolutePath());

        //使用完之后删除
        //temp.delete();

        String path2 = "E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\oldtravle-0.0.1-SNAPSHOT.jar!\\BOOT-INF\\classes!\\static\\travelRoute\\1621392760036.jpg";
        String path3 = "E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\oldtravle-0.0.1-SNAPSHOT.jar!";
        String path4 = "E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\";
        String path5 = "E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\oldtravle-0.0.1-SNAPSHOT.jar";
        File file5 = new File(path5);
        System.out.println(file5.exists());
        File file4 = new File(path4);
        System.out.println(file4.exists());
        File file3 = new File(path3);
        System.out.println(file3.exists());
        File file2 = new File(path2);
        System.out.println(file2.exists());
        File file6 = new File("E:\\IntelliJ IDEA\\IntelliJ IDEA4\\SpringBoot\\oldtravle\\target\\oldtravle-0.0.1-SNAPSHOT.jar!\\BOOT-INF\\classes!\\static\\travelRoute\\1621392760036.jpg");
        System.out.println(file6.exists());
    }
}
