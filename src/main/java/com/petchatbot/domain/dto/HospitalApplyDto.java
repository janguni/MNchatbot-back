package com.petchatbot.domain.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class HospitalApplyDto {
    private int petSerial;
    private int medicalSerial; //문진표
    private int partnerSerial;
    private Date apptDate;
    private String apptTime;
    private String apptMemberName;
    private String apptMemberTel;
    private boolean apptBill;
    private String apptReason;
    private String apptImage;

    public HospitalApplyDto() {

    }

    public HospitalApplyDto(int petSerial, int medicalSerial, int partnerSerial, Date apptDate, String apptTime, String apptMemberName, String apptMemberTel, boolean apptBill, String apptReason, String apptImage) {
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
