package com.petchatbot.domain.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Table(name = "members")
@Getter
@Entity
@Slf4j
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_serial")
    private int memberSerial;
    @Column(name = "member_email")
    private String memberEmail;
    @Column(name = "member_password")
    private String memberPassword;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> petList = new ArrayList<Pet>();

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MedicalForm> medicalFormList = new ArrayList<MedicalForm>();

    public Member() {
    }

    public Member(String memberEmail, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }

    public void addPet(Pet pet){
        petList.add(pet);
    }


    public void changePassword(String password){
        this.memberPassword = password;
    }


}
