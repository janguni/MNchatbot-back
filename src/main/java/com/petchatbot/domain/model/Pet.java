package com.petchatbot.domain.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petSerial;

    @ManyToOne
    @JoinColumn(name="member_serial")
    private Member member;
    @Enumerated(EnumType.STRING)
    private Breed petBreed;
    private String petName;
    private int petAge;
    @Enumerated(EnumType.STRING)
    private PetSex petSex;
    @Enumerated(EnumType.STRING)
    private Neutralization petNeutralization;

    public Pet() {
    }

    public Pet(Breed petBreed, String petName, int petAge, PetSex petSex, Neutralization petNeutralization) {
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petSex = petSex;
        this.petNeutralization = petNeutralization;
    }

    public void changePetInfo(Breed petBreed, String petName, int petAge, PetSex petSex,  Neutralization petNeutralization){
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petSex = petSex;
        this.petNeutralization = petNeutralization;
    }

    public void addMember(Member member) {
        this.member = member;
    }



}