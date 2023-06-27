package com.heyprojecthub.ilproperty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.heyprojecthub.ilproperty.mapper")
public class IlPropertyApplication {

    public static void main(String[] args) {
        SpringApplication.run(IlPropertyApplication.class, args);
    }

}
