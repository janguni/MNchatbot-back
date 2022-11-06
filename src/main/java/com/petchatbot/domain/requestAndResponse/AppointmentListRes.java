package com.petchatbot.domain.requestAndResponse;

import lombok.Data;

@Data
public class AppointmentListRes {
    private int apptSerial;
    private String partnerName;
    private String apptDate;

    public AppointmentListRes(int apptSerial, String partnerName, String apptDate) {
        this.apptSerial = apptSerial;
        this.partnerName = partnerName;
        this.apptDate = apptDate;
    }
}
