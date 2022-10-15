package com.petchatbot.domain.requestAndResponse;

import lombok.Data;

@Data
public class ExpectDiagListRes {
    private int diagSerial;
    private String diagDsName;
    private String diagDate;

    public ExpectDiagListRes() {
    }

    public ExpectDiagListRes(int diagSerial, String diagDsName, String diagDate) {
        this.diagSerial = diagSerial;
        this.diagDsName = diagDsName;
        this.diagDate = diagDate;
    }
}
