package com.petchatbot.domain.model;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class Diseases {

    @Id
    private ObjectId _Id;
    private String DsName; // 질병이름
    private String DsAmlBreed; // 축종
    private String DsDefinition; // 정의
    private String DsCause; //
    private String DsPathogenesis;
    private String DsEpidemiology;
    private String DsSymptom; // 증상
    private String DsDiagnosis;
    private String DsTreatment; // 치료
    private String DsPrevention; // 예방
    private String DsPrognosis;
    private String DsAdvice; // 조언

    public Diseases() {
    }

    public Diseases(ObjectId _Id, String dsName, String dsAmlBreed, String dsDefinition, String dsCause, String dsPathogenesis, String dsEpidemiology, String dsSymptom, String dsDiagnosis, String dsTreatment, String dsPrevention, String dsPrognosis, String dsAdvice) {
        this._Id = _Id;
        DsName = dsName;
        DsAmlBreed = dsAmlBreed;
        DsDefinition = dsDefinition;
        DsCause = dsCause;
        DsPathogenesis = dsPathogenesis;
        DsEpidemiology = dsEpidemiology;
        DsSymptom = dsSymptom;
        DsDiagnosis = dsDiagnosis;
        DsTreatment = dsTreatment;
        DsPrevention = dsPrevention;
        DsPrognosis = dsPrognosis;
        DsAdvice = dsAdvice;
    }
}
