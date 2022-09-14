package com.petchatbot.domain.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pnr_serial")
    private int pnrSerial;

    @Column(name = "pnr_name")
    private String pnrName;

    @Column(name = "pnr_tel")
    private String pnrTel;

    @Column(name = "pnr_email")
    private String pnrEmail;

    @Column(name = "pnr_address")
    private String pnrAddress;

    @Column(name = "pnr_region")
    private String pnrRegion;

    @Column(name = "pnr_city")
    private String pnrCity;

    @Column(name = "pnr_street")
    private String pnrStreet;

    @Column(name = "pnr_field")
    private String pnrField;

    public Partner() {
    }

    public Partner(String pnrName, String pnrTel, String pnrEmail, String pnrAddress, String pnrRegion, String pnrCity, String pnrStreet, String pnrField) {
        this.pnrName = pnrName;
        this.pnrTel = pnrTel;
        this.pnrEmail = pnrEmail;
        this.pnrAddress = pnrAddress;
        this.pnrRegion = pnrRegion;
        this.pnrCity = pnrCity;
        this.pnrStreet = pnrStreet;
        this.pnrField = pnrField;
    }
}
