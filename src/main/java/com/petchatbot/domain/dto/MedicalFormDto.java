package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.util.Date;

@Data
public class MedicalFormDto {

    private int petSerial; // 단방향 해야 할듯

    private int memberSerial;

    private String medicalFormName;

    private Date medicalFormDate;

    private Time medicalFormTime;

    private String medicalFormQ1;

    private boolean medicalFormQ2;

    private String medicalFormQ3;

    private boolean medicalFormQ4;

    private boolean medicalFormQ5;

    private boolean medicalFormQ6;

    private String medicalFormQ7;

    public MedicalFormDto() {
    }

    public MedicalFormDto(int petSerial, int memberSerial, String medicalFormName, Date medicalFormDate, Time medicalFormTime, String medicalFormQ1, boolean medicalFormQ2, String medicalFormQ3, boolean medicalFormQ4, boolean medicalFormQ5, boolean medicalFormQ6, String medicalFormQ7) {
        this.petSerial = petSerial;
        this.memberSerial = memberSerial;
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
