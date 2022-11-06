package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.model.PetGender;
import com.petchatbot.domain.model.Species;
import lombok.Data;

import java.net.URL;

@Data
public class AppointmentInfoRes {
    private String apptDate;
    private String apptTime;
    private String apptMemberName;
    private String apptMemberTell;
    private String petName;
    private int petAge;
    private Species petSpecies; //축종
    private String petBreed; // 품종
    private PetGender petGender;
    private String isNeutralization;
    private String underDisease;
    private String isSpecialNoteMedication;
    private String medication;
    private String isSurgeryOrAne;
    private String isHyperExercise;
    private String etc;
    private String apptReason;
    private String isCostRequest;
    private URL apptImage;

    public AppointmentInfoRes(String apptDate, String apptTime, String apptMemberName, String apptMemberTell, String petName, int petAge, Species petSpecies, String petBreed, PetGender petGender, String isNeutralization, String underDisease, String medication, String isSpecialNoteMedication, String isSurgeryOrAne, String isHyperExercise, String etc, String apptReason, String isCostRequest, URL apptImage) {
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.apptMemberName = apptMemberName;
        this.apptMemberTell = apptMemberTell;
        this.petName = petName;
        this.petAge = petAge;
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.isNeutralization = isNeutralization;
        this.underDisease = underDisease;
        this.medication = medication;
        this.isSpecialNoteMedication = isSpecialNoteMedication;
        this.isSurgeryOrAne = isSurgeryOrAne;
        this.isHyperExercise = isHyperExercise;
        this.etc = etc;
        this.apptReason = apptReason;
        this.isCostRequest = isCostRequest;
        this.apptImage = apptImage;
    }
}
