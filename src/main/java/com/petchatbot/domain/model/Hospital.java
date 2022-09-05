package com.petchatbot.domain.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document("animal_hospitals")
@Getter
public class Hospital {

    @Id
    private String id;
    @Field(name = "hosp_tel")
    private String hospTel;
    @Field(name = "hosp_region")
    private String hospRegion;
    @Field(name = "hosp_city")
    private String hospCity;
    @Field(name = "hosp_street")
    private String hospStreet;
    @Field(name = "hosp_name")
    private String hospName;
    @Field(name = "hosp_address")
    private String hospAddress;

}
