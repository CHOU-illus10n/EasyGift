package com.ncepu.easygift;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@ComponentScan(basePackages = {"com.ncepu"})
@MapperScan(basePackages = {"com.ncepu.easygift.mapper"})
@EnableTransactionManagement
@SpringBootApplication // 启动类
public class EGWxApplication {
    public static void main(String[] args) {
        SpringApplication.run(EGWxApplication.class, args);
    }

    void test() {
        System.out.println("test");
    }
}
