package com.petchatbot.domain.dto;

import lombok.Getter;

@Getter
public class EmailCodeDto {
    private int sendCode;
    private int receivedCode;

    private String memberEmail;
    private String memberPassword;

    public EmailCodeDto() {
    }

    public EmailCodeDto(int sendCode, int receivedCode, String memberEmail, String memberPassword) {
        this.sendCode = sendCode;
        this.receivedCode = receivedCode;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }
}
