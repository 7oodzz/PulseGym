package com.springdemo.pulsegym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PulseGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulseGymApplication.class, args);
    }

}
