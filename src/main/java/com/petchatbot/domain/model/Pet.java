package com.petchatbot.domain.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(name = "pets")
@Getter
@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_serial")
    private Long petSerial;

    @JoinColumn(name="members_member_serial")
    @ManyToOne
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
    @Column(name = "pet_sex")
    private PetGender petGender;
    @Enumerated(EnumType.STRING)
    @Column(name = "pet_neutralization")
    private Neutralization petNeutralization;

//    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Pet> petList = new ArrayList<Pet>();



    public Pet() {
    }

    public Pet(Member member, Species petSpecies, String petBreed, String petName, int petAge, PetGender petGender, Neutralization petNeutralization) {
        this.member = member;
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