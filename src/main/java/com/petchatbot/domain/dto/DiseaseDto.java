package com.petchatbot.domain.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
public class DiseaseDto {

    private String dsName;

    private String dsAmlBreed;

    private String dsDefinition;

    private String dsCause;

    private String dsPathogenesis;

    private String dsEpidemiology;

    private String dsSymptom;

    private String dsDiagnosis;

    private String dsTreatment;

    private String dsPrevention;

    private String dsPrognosis;

    private String dsAdvice;
}
