package com.petchatbot.domain.requestAndResponse;

import lombok.Data;

@Data
public class ChangePwReq {
    private String MemberEmail;
    private String MemberNewPassword;
}
