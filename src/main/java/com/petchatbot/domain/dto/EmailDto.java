package com.petchatbot.domain.dto;

import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Getter
public class EmailDto {
    @Id
    private String receiveMail;

    public EmailDto() {}

    public EmailDto(String receiveMail) {
        this.receiveMail = receiveMail;
    }
}
