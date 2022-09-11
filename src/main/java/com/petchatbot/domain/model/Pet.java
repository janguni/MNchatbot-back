package com.petchatbot.domain.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Data
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petSerial;

    @ManyToOne
    @JoinColumn(name="member_serial")
    private Member member;
    @Enumerated(EnumType.STRING)
    @Column(name = "pet_species")
    private Species petSpecies;

    @Column(name = "pet_breed")
    private String petBreed;
    @Column(name = "pet_name")
    private String petName;
    @Column(name = "pet_age")
    private int petAge;
    @Enumerated(EnumType.STRING)
    @Column(name = "pet_gender")
    private PetGender petGender;
    @Enumerated(EnumType.STRING)
    @Column(name = "pet_neutralization")
    private Neutralization petNeutralization;

    public Pet() {
    }

    public Pet(Species petSpecies, String petBreed, String petName, int petAge, PetGender petGender, Neutralization petNeutralization) {
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petNeutralization = petNeutralization;
    }

    public void changePetInfo(Species petSpecies, String petBreed, String petName, int petAge, PetGender petgender, Neutralization petNeutralization){
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petGender = petgender;
        this.petNeutralization = petNeutralization;
    }

    public void addMember(Member member) {
        this.member = member;
    }

}