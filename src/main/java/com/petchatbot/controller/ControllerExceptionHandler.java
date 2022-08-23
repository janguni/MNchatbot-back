package com.petchatbot.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice

public class ControllerExceptionHandler {

    // 유효성 검사 오류시 오류 메시지를 반환
    public String validationException(RuntimeException e){
        return e.getMessage();
    }
}
