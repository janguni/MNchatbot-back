package com.petchatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class startApp {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadta","true");
    }
    public static void main(String[] args) {
        SpringApplication.run(startApp.class,args);
    }
}
