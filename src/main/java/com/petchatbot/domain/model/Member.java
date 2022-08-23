package com.petchatbot.domain.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Entity
@Slf4j
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_serial;
    private String memberEmail;
    private String memberPassword;
    private String roles; // USER, ADMIN

    @OneToMany(mappedBy = "member")
    private List<Pet> petList = new ArrayList<>();

    public Member() {
    }

    public Member(String memberEmail, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }

    public void addPet(Pet pet){
        petList.add(pet);
        pet.addMember(this);
        log.info("pet.addMember={}", pet.getMember());
    }


    public void changePassword(String password){
        this.memberPassword = password;
    }

}
