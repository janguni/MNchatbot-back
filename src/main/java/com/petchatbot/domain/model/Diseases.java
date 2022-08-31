package com.petchatbot.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "animal_diseases")
@Getter
@NoArgsConstructor
public class Diseases {

    @Id
    private ObjectId id;
    private String ds_name; // 질병이름
    private String ds_aml_breed; // 축종
    private String ds_definition; // 정의
    private String ds_cause; //
    private String ds_pathogenesis;
    private String ds_epidemiology;
    private String ds_symptom; // 증상
    private String ds_diagnosis;
    private String ds_treatment; // 치료
    private String ds_prevention; // 예방
    private String ds_prognosis;
    private String ds_advice; // 조언

}
