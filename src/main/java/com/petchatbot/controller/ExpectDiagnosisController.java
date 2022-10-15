package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.MedicalFormDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.ExpectDiagListRes;
import com.petchatbot.service.ExpectDiagnosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExpectDiagnosisController {
    private final ExpectDiagnosisService expectDiagnosisService;

    // 예상진단 목록
    @GetMapping("/expectDiag/expectDiagList/{petSerial}")
    public ResponseEntity<List<ExpectDiagListRes>> addMedicalForm(@PathVariable("petSerial") int petSerial) {
        List<ExpectDiagListRes> expectDiagList = expectDiagnosisService.getExpectDiagList(petSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_EXPECTDIAG_LIST, expectDiagList), HttpStatus.OK);
    }

}
