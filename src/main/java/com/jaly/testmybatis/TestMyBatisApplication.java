package com.jaly.testmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jaly.testmybatis.infra.mapper")
public class TestMyBatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMyBatisApplication.class, args);
    }

}
