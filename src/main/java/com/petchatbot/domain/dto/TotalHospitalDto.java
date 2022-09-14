package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.HospitalType;
import lombok.Data;

@Data
public class TotalHospitalDto {
    private String totalHospName;
    private String totalHospAddress;
    private String totalHospEmail;
    private String totalHospTel;
    private String totalHospField;

    private HospitalType totalHospType;

    public TotalHospitalDto() {
    }

    public TotalHospitalDto(String totalHospName, String totalHospAddress, String totalHospEmail, String totalHospTel, String totalHospField, HospitalType totalHospType) {
        this.totalHospName = totalHospName;
        this.totalHospAddress = totalHospAddress;
        this.totalHospEmail = totalHospEmail;
        this.totalHospTel = totalHospTel;
        this.totalHospField = totalHospField;
        this.totalHospType = totalHospType;
    }

    public TotalHospitalDto(String totalHospName, String totalHospAddress, String totalHospTel, HospitalType totalHospType) {
        this.totalHospName = totalHospName;
        this.totalHospAddress = totalHospAddress;
        this.totalHospTel = totalHospTel;
        this.totalHospType = totalHospType;
    }


}
