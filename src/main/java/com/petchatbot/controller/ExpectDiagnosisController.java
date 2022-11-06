package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.MedicalFormDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.requestAndResponse.*;
import com.petchatbot.service.ExpectDiagnosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExpectDiagnosisController {
    private final ExpectDiagnosisService expectDiagnosisService;

    @PostMapping("/expectDiag/add")
    public ResponseEntity<ExpectDiagInfoRes> addExpectDiag(@RequestBody PetSerialReq petReq,Authentication authentication) {
        int petSerial = petReq.getPetSerial();
        String memberEmail = extractEmail(authentication);

        String url = "http://43.200.87.239:5000/tospring";
        String sb = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line + "\n";
            }
            br.close();
            expectDiagnosisService.addExpectDiag(petSerial, memberEmail, sb);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_ADD_EXPECTDIAG), HttpStatus.OK);
    }

    // 예상진단 목록
    @GetMapping("/expectDiag/expectDiagList/{petSerial}")
    public ResponseEntity<List<ExpectDiagListRes>> getExpectDiagList(@PathVariable("petSerial") int petSerial) {
        List<ExpectDiagListRes> expectDiagList = expectDiagnosisService.getExpectDiagList(petSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_EXPECTDIAG_LIST, expectDiagList), HttpStatus.OK);
    }

    // 예상진단 세부목록
    @GetMapping("/expectDiag/{expectDiagSerial}")
    public ResponseEntity<ExpectDiagInfoRes> getExpectDiagInfo(@PathVariable("expectDiagSerial") int diagSerial) {
        log.info("예상진단 세부정보 ={}", diagSerial);
        ExpectDiagInfoRes expectDiagInfo = expectDiagnosisService.getExpectDiagInfo(diagSerial);
        log.info("예상진단 세부정보 전송= {}", expectDiagInfo.getDefinition());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_EXPECTDIAG_INFO, expectDiagInfo), HttpStatus.OK);
    }

    // 예상진단 삭제
    @DeleteMapping("/expectDiag/delete/{expectDiagSerial}")
    public ResponseEntity<String> deleteExpectDiag(@PathVariable("expectDiagSerial") int diagSerial) {
        expectDiagnosisService.deleteExpectDiag(diagSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE_EXPECTDIAG), HttpStatus.OK);
    }

    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}
