package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class SpringbootFirstPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFirstPracticeApplication.class, args);
    }
}
