package com.petchatbot.domain.requestAndResponse;

import lombok.Data;

@Data
public class JoinReq {
    private String MemberEmail;
    private String MemberPassword;
}
