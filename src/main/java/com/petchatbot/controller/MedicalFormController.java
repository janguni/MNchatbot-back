package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.*;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.requestAndResponse.ChangeMedicalFormReq;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.MedicalFormRes;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.service.MedicalFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MedicalFormController {

    private final MedicalFormService medicalFormService;
    private final MemberRepository memberRepository;

    // 문진표 등록
    @PostMapping("/medicalForm/add")
    public ResponseEntity<MedicalFormDto> addMedicalForm(@RequestBody MedicalFormDto medicalFormDto, Authentication authentication) throws ParseException {
        String memberEmail = extractEmail(authentication);
        Member byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        medicalFormService.saveMedicalForm(medicalFormDto, byMemberEmail);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_ADD_MEDICAL_FORM), HttpStatus.OK);
    }

    // 문진표 목록
    @GetMapping("/medicalForm/medicalFormList/{petSerial}")
    public ResponseEntity<List<MedicalFormListDto>> getMedicalFormList(@PathVariable("petSerial") int petSerial) {

        List<MedicalFormListDto> medicalFormList = medicalFormService.getMedicalFormList(petSerial);
        if (medicalFormList.isEmpty()){
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_MEDICAL_FORM_LIST), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_MEDICAL_FORM_LIST, medicalFormList), HttpStatus.OK);
    }

    // 문진표 세부정보
    @GetMapping("/medicalForm/{medicalFormSerial}")
    public ResponseEntity<MedicalFormRes> getMedicalFormInfo(@PathVariable("medicalFormSerial") int medicalFormSerial) {

        MedicalFormRes medicalFormInfo = medicalFormService.getMedicalFormInfo(medicalFormSerial);
        log.info("date={}", medicalFormInfo.getMedicalFormDate());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_MEDICAL_FORM_INFO, medicalFormInfo), HttpStatus.OK);
    }

    // 문진표 수정
    @PatchMapping("/medicalForm/update")
    public ResponseEntity<MedicalFormDto> updateMedicalForm(@RequestBody ChangeMedicalFormReq changeMedicalFormDto, Authentication authentication) {
        String memberEmail = extractEmail(authentication);
        Member findMember = memberRepository.findByMemberEmail(memberEmail);
        medicalFormService.updateMedicalForm(changeMedicalFormDto, findMember);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_UPDATE_MEDICAL_FORM), HttpStatus.OK);
    }

    // 문진표 삭제
    @DeleteMapping("/medicalForm/delete/{medicalFormSerial}")
    public ResponseEntity<MedicalFormDto> updateMedicalForm(@PathVariable("medicalFormSerial") int medicalFormSerial) {
        medicalFormService.deleteMedicalForm(medicalFormSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE_MEDICAL_FORM), HttpStatus.OK);
    }

    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}
