package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.model.Breed;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.PetSex;
import lombok.Data;

@Data
public class ChangePetInfoReq {
    private Long petSerial;
    private Breed petBreed;
    private String petName;
    private int petAge;
    private PetSex petSex ;
    private Neutralization petNeutralization ;
}
