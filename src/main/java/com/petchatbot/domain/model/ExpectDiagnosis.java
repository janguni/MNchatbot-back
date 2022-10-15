package com.petchatbot.domain.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Table(name = "expect_diagnoses")
@Getter
@Entity
public class ExpectDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diag_serial")
    private int diagSerial;

    @JoinColumn(name = "pets_pet_serial")
    @ManyToOne
    private Pet pet;

    @JoinColumn(name = "pets_members_member_serial")
    @ManyToOne
    private Member member;

    @Column(name = "diag_date")
    private Date diagDate;

    @Column(name = "diag_time")
    private Time diagTime;

    @Column(name = "diag_ds_name")
    private String diagDsName; // 예상 질병명

    public ExpectDiagnosis() {
    }

    public ExpectDiagnosis(Pet pet, Member member, Date diagDate, Time diagTime, String diagDsName) {
        this.pet = pet;
        this.member = member;
        this.diagDate = diagDate;
        this.diagTime = diagTime;
        this.diagDsName = diagDsName;
    }
}
