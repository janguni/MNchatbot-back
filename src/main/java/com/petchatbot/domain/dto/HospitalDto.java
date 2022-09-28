package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.HospitalType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class HospitalDto {

    private int hospSerial;
    private String hospName;
    private String hospAddress;
    private String hospTel;

    private HospitalType hospType;

    public HospitalDto() {
    }

    public HospitalDto(String hospName, String hospAddress, String hospTel, HospitalType hospitalType) {
        this.hospName = hospName;
        this.hospAddress = hospAddress;
        this.hospTel = hospTel;
        this.hospType = hospitalType;
    }

    public HospitalDto(int hospSerial, String hospName, String hospAddress, String hospTel, HospitalType hospitalType) {
        this.hospSerial = hospSerial;
        this.hospName = hospName;
        this.hospAddress = hospAddress;
        this.hospTel = hospTel;
        this.hospType = hospitalType;
    }
}
