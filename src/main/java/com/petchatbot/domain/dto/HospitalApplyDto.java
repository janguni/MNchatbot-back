package com.petchatbot.domain.dto;

import lombok.Data;
import org.apache.tomcat.jni.Time;

import java.util.Date;

@Data
public class HospitalApplyDto {
    private int petSerial;
    //private int medicalSerial; //문진표
    private int memberSerial;
    private int partnerSerial;
    private Date apptDate;
    private Time apptTime;
    private String apptMemberName;
    private String apptMemberTel;
    private String apptBill;
    private String apptReason;
    private String apptImage;

    public HospitalApplyDto() {

    }

    public HospitalApplyDto(int petSerial, int memberSerial, int partnerSerial, Date apptDate, Time apptTime, String apptMemberName, String apptMemberTel, String apptBill, String apptReason, String apptImage) {
        this.petSerial = petSerial;
        this.memberSerial = memberSerial;
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
