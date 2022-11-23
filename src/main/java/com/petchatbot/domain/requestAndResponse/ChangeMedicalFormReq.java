package com.petchatbot.domain.requestAndResponse;

import lombok.Data;

import java.util.Date;

@Data
public class ChangeMedicalFormReq {
    private int medicalFormSerial;
    private String medicalFormName;

    private Date medicalFormDate;

    private String medicalFormTime;

    private String medicalFormQ1;

    private String medicalFormQ2;

    private boolean medicalFormQ3;

    private String medicalFormQ4;

    private boolean medicalFormQ5;

    private boolean medicalFormQ6;

    private String medicalFormQ7;

}
