package com.petchatbot.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    private MultipartFile apptImage; // 반려동물 사진
}
