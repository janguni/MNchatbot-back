package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.Species;
import lombok.Data;

@Data
public class PetListDto {
    private int petSerial;
    private Species petSpecies;
    private String petName;


    public PetListDto(int petSerial, Species petSpecies, String petName) {
        this.petSerial = petSerial;
        this.petSpecies = petSpecies;
        this.petName = petName;
    }
}
