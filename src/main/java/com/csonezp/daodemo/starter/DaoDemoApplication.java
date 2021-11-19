package com.csonezp.daodemo.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.csonezp.daodemo.*"})
public class DaoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaoDemoApplication.class, args);
    }

}
