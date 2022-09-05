package com.petchatbot.domain.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class HospitalDto {
    private String hospName;
    private String hospAddress;
    private String hospTel;

    public HospitalDto() {
    }

    public HospitalDto(String hospName, String hospAddress, String hospTel) {
        this.hospName = hospName;
        this.hospAddress = hospAddress;
        this.hospTel = hospTel;
    }
}
