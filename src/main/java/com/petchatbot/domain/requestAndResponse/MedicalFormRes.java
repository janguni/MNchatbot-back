package com.petchatbot.domain.requestAndResponse;

import lombok.Data;

import java.util.Date;

@Data
public class MedicalFormRes {
    private String medicalFormName;

    private String medicalFormDate;

    private String medicalFormTime;

    private String medicalFormQ1;

    private String medicalFormQ2;

    private boolean medicalFormQ3;

    private String medicalFormQ4;

    private boolean medicalFormQ5;

    private boolean medicalFormQ6;

    private String medicalFormQ7;

    public MedicalFormRes() {
    }

    public MedicalFormRes(String medicalFormName, String medicalFormDate, String medicalFormTime, String medicalFormQ1, String medicalFormQ2, boolean medicalFormQ3, String medicalFormQ4, boolean medicalFormQ5, boolean medicalFormQ6, String medicalFormQ7) {
        this.medicalFormName = medicalFormName;
        this.medicalFormDate = medicalFormDate;
        this.medicalFormTime = medicalFormTime;
        this.medicalFormQ1 = medicalFormQ1;
        this.medicalFormQ2 = medicalFormQ2;
        this.medicalFormQ3 = medicalFormQ3;
        this.medicalFormQ4 = medicalFormQ4;
        this.medicalFormQ5 = medicalFormQ5;
        this.medicalFormQ6 = medicalFormQ6;
        this.medicalFormQ7 = medicalFormQ7;
    }
}
