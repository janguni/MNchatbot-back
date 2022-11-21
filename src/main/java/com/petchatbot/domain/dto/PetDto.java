package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.Species;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.PetGender;
import lombok.Data;

@Data
public class PetDto {
    private Species petSpecies;
    private String petBreed;
    private String petName;
    private int petAge;
    private PetGender petGender;
    private Neutralization petNeutralization;

    public PetDto(Species petSpecies, String petBreed, String petName, int petAge, PetGender petGender, Neutralization petNeutralization) {
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petNeutralization = petNeutralization;
    }
}
