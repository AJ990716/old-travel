package com.willpower.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.willpower.travel.mapper") //读取mapper配置注解生成对象  或者在mapper文件上单独加Mapper
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}