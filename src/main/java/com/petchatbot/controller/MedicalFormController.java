package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.ChangeMedicalFormDto;
import com.petchatbot.domain.dto.HospitalDto;
import com.petchatbot.domain.dto.MedicalFormDto;
import com.petchatbot.domain.dto.TotalHospitalDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.PetReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.service.HospitalService;
import com.petchatbot.service.MedicalFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MedicalFormController {

    private final MedicalFormService medicalFormService;
    private final MemberRepository memberRepository;

    @PostMapping("/medicalForm/add")

    public ResponseEntity<MedicalFormDto> addMedicalForm(@RequestBody MedicalFormDto medicalFormDto, Authentication authentication) throws ParseException {
        String memberEmail = extractEmail(authentication);
        Member byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        medicalFormService.saveMedicalForm(medicalFormDto, byMemberEmail);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_ADD_MEDICAL_FORM), HttpStatus.OK);
    }

//    @PatchMapping("/medicalForm/update")
//    public ResponseEntity<MedicalFormDto> updateMedicalForm(@RequestBody ChangeMedicalFormDto changeMedicalFormDto, Authentication authentication) {
//        String memberEmail = extractEmail(authentication);
//        Member byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
//        //medicalFormService.saveMedicalForm(medicalFormDto, byMemberEmail);
//        //return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_ADD_MEDICAL_FORM), HttpStatus.OK);
//    }



    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}
