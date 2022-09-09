package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.Species;
import lombok.Data;

@Data
public class PetListDto {
    private Long petSerial;
    private Species petSpecies;
    private String petName;


    public PetListDto(Long petSerial, Species petSpecies, String petName) {
        this.petSerial = petSerial;
        this.petSpecies = petSpecies;
        this.petName = petName;
    }
}
