package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.model.Breed;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.PetSex;
import lombok.Data;

@Data
public class PetRegReq {
    private String memberEmail;
    private Breed petBreed;
    private String petName;
    private int petAge;
    private PetSex petSex;
    private Neutralization petNeutralization;

    public PetRegReq() {
    }

    public PetRegReq(String memberEmail, Breed petBreed, String petName, int petAge, PetSex petSex, Neutralization petNeutralization) {
        this.memberEmail = memberEmail;
        this.petBreed = petBreed;
        this.petName = petName;
        this.petAge = petAge;
        this.petSex = petSex;
        this.petNeutralization = petNeutralization;
    }
}
