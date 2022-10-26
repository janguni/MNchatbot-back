package com.petchatbot.domain.requestAndResponse;

import com.petchatbot.domain.dto.DiseaseDto;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class ExpectDiagInfoRes {
    private Date date;
    private String time;
    private String diseaseName;
    private String breed;
    private String definition;
    private String cause;
    private String advice;

    public ExpectDiagInfoRes() {

    }

    public ExpectDiagInfoRes(Date date, String time, String diseaseName, String breed, String definition, String cause, String advice) {
        this.date = date;
        this.time = time;
        this.diseaseName = diseaseName;
        this.breed = breed;
        this.definition = definition;
        this.cause = cause;
        this.advice = advice;
    }
}
