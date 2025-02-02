package com.petchatbot.domain.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Table(name = "medical_forms")
@Getter
@Entity
@Slf4j
public class MedicalForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "med_serial")
    private int medicalFormSerial;

    @JoinColumn(name = "pets_pet_serial")
    @ManyToOne
    private Pet pet; // 단방향 해야 할듯

    @JoinColumn(name = "pets_members_member_serial")
    @ManyToOne
    private Member member;

    @Column(name = "med_name")
    private String medicalFormName;

    @Column(name = "med_date")
    private Date medicalFormDate;

    @Column(name = "med_time")
    private String medicalFormTime;

    @Column(name = "med_q1")
    private String medicalFormQ1; // 이유

    @Column(name = "med_q2")
    private String medicalFormQ2; // 기저질환

    @Column(name = "med_q3")
    private boolean medicalFormQ3; //약에 대한 특이사항 여부

    @Column(name = "med_q4")
    private String medicalFormQ4; //약 관련내용

    @Column(name = "med_q5")
    private boolean medicalFormQ5; // 수출, 마취 이력 여부

    @Column(name = "med_q6")
    private boolean medicalFormQ6; // 과도한 운동 여부

    @Column(name = "med_q7")
    private String medicalFormQ7; // 기타 특이사항

    public MedicalForm() {
    }

    public MedicalForm(Pet pet, Member member, String medicalFormName, Date medicalFormDate, String medicalFormTime, String medicalFormQ1, String medicalFormQ2, boolean medicalFormQ3, String medicalFormQ4, boolean medicalFormQ5, boolean medicalFormQ6, String medicalFormQ7) {
        this.pet = pet;
        this.member = member;
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

    public void changeMedicalForm (String medicalFormName, String medicalFormQ1, String medicalFormQ2, boolean medicalFormQ3, String medicalFormQ4, boolean medicalFormQ5, boolean medicalFormQ6, String medicalFormQ7){
        this.medicalFormName = medicalFormName;
        this.medicalFormQ1 = medicalFormQ1;
        this.medicalFormQ2 = medicalFormQ2;
        this.medicalFormQ3 = medicalFormQ3;
        this.medicalFormQ4 = medicalFormQ4;
        this.medicalFormQ5 = medicalFormQ5;
        this.medicalFormQ6 = medicalFormQ6;
        this.medicalFormQ7 = medicalFormQ7;
    }
}
