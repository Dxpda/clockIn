package com.da.clockin;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.da.clockin.mapper")
public class ClockInApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClockInApplication.class, args);
    }

}
