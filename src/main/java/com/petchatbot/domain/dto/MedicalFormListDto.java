package com.petchatbot.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MedicalFormListDto {

    public int medicalFormSerial;
    public String medicalFormName;
    public String medicalFormDate;

    public MedicalFormListDto() {
    }

    public MedicalFormListDto(int medicalFormSerial, String medicalFormName, String medicalFormDate) {
        this.medicalFormSerial = medicalFormSerial;
        this.medicalFormName = medicalFormName;
        this.medicalFormDate = medicalFormDate;
    }
}
