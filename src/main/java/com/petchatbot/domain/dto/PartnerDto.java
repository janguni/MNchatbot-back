package com.petchatbot.domain.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PartnerDto {

    private String pnrName;
    private String pnrTel;
    private String pnrEmail;
    private String pnrAddress;
    private String pnrField;

    public PartnerDto() {
    }

    public PartnerDto(String pnrName, String pnrTel, String pnrEmail, String pnrAddress, String pnrField) {
        this.pnrName = pnrName;
        this.pnrTel = pnrTel;
        this.pnrEmail = pnrEmail;
        this.pnrAddress = pnrAddress;
        this.pnrField = pnrField;
    }
}
