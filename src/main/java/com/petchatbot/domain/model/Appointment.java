package com.petchatbot.domain.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Table(name = "appointments")
@Getter
@Entity
@Slf4j
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appt_serial")
    private int appointmentSerial;

    @JoinColumn(name = "pets_pet_serial")
    @ManyToOne
    private Pet pet; // 단방향 해야 할듯

    @JoinColumn(name = "pets_members_member_serial")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "medical_forms_med_serial")
    @ManyToOne
    private MedicalForm medicalForm;

    @JoinColumn(name = "partners_pnr_serial")
    @ManyToOne
    private Partner partner;

    @Column(name = "appt_date")
    private Date appointmentDate;

    @Column(name = "appt_time")
    private String appointmentTime;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_tel")
    private String memberTel;

    @Column(name = "appt_bill")
    private boolean isWantBillInfo;

    @Column(name = "appt_reason")
    private String appointmentReason;

    @Column(name = "appt_image")
    private String appointmentImage;

    public Appointment() {
    }

    public Appointment(Pet pet, Member member, MedicalForm medicalForm, Partner partner, Date appointmentDate, String appointmentTime, String memberName, String memberTel, boolean isWantBillInfo, String appointmentReason, String appointmentImage) {
        this.pet = pet;
        this.member = member;
        this.medicalForm = medicalForm;
        this.partner = partner;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.isWantBillInfo = isWantBillInfo;
        this.appointmentReason = appointmentReason;
        this.appointmentImage = appointmentImage;
    }
}
