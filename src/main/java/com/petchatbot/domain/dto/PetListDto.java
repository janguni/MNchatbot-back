package com.petchatbot.domain.dto;

import com.petchatbot.domain.model.Breed;
import lombok.Data;

@Data
public class PetListDto {
    private String petName;
    private Long petSerial;
    private Breed breed;

    public PetListDto(String petName, Long petSerial, Breed petBreed) {
        this.petName = petName;
        this.petSerial = petSerial;
        this.breed =  petBreed;
    }
}
