package com.petchatbot.domain.dto;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberDto {

    @Id
    @Column(nullable = false, unique = true, length=50)
    private String memberEmail;
    @Column(nullable = false, unique = true, length=50)
    private String memberPassword;

    public MemberDto() {
    }

    public MemberDto(String memberEmail, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }
}
