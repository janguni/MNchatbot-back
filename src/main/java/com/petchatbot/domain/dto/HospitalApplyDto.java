package com.petchatbot.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.Date;

@Data
public class HospitalApplyDto {
    private String petSerial;
    private String medicalSerial; //문진표
    private String partnerSerial;
    private String apptDate;
    private String apptTime;
    private String apptMemberName;
    private String apptMemberTel;
    private String apptBill;
    private String apptReason;
    private String apptImage;

    public HospitalApplyDto() {

    }

    public HospitalApplyDto(String petSerial, String medicalSerial, String partnerSerial, String apptDate, String apptTime, String apptMemberName, String apptMemberTel, String apptBill, String apptReason, String apptImage) {
        this.petSerial = petSerial;
        this.medicalSerial = medicalSerial;
        this.partnerSerial = partnerSerial;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.apptMemberName = apptMemberName;
        this.apptMemberTel = apptMemberTel;
        this.apptBill = apptBill;
        this.apptReason = apptReason;
        this.apptImage = apptImage;
    }
}
