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

    private String medicalFormTime;

    private String medicalFormQ1;

    private String medicalFormQ2;

    private boolean medicalFormQ3;

    private String medicalFormQ4;

    private boolean medicalFormQ5;

    private boolean medicalFormQ6;

    private String medicalFormQ7;

}
