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

import java.util.List;


// 1. 문진표 추가
// 2. 문진표 목록 확인
// 3. 문진표 세부정보 확인
// 4. 문진표 수정
// 5. 문진표 삭제


@RestController
@RequiredArgsConstructor
@Slf4j
public class MedicalFormController {

    private final MedicalFormService medicalFormService;
    private final MemberRepository memberRepository;

    /**
     * 문진표 추가
     * @param medicalFormDto (펫 시리얼, 멤버 시리얼, 날짜, 시간, 문진표 문항들) -> 사용자 시리얼은 삭제할 예정
     * @param authentication (Jwt 활용)
     * @return 정상:200 / 그 외 처리x
     */
    @PostMapping("/medicalForm/add")
    public ResponseEntity<MedicalFormDto> addMedicalForm(@RequestBody MedicalFormDto medicalFormDto, Authentication authentication) {
        Member findMember = extractMember(authentication);
        medicalFormService.saveMedicalForm(medicalFormDto, findMember);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_ADD_MEDICAL_FORM), HttpStatus.OK);
    }


    /**
     * 문진표 목록 확인
     * @param petSerial
     * @return 정상:200 / 그 외 처리x
     */
    @GetMapping("/medicalForm/medicalFormList/{petSerial}")
    public ResponseEntity<List<MedicalFormListDto>> getMedicalFormList(@PathVariable("petSerial") int petSerial) {
        List<MedicalFormListDto> medicalFormList = medicalFormService.getMedicalFormList(petSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_MEDICAL_FORM_LIST, medicalFormList), HttpStatus.OK);
    }


    /**
     * 문진표 세부정보 확인
     * @param medicalFormSerial
     * @return 정상:200 / 그 외 처리x
     */
    @GetMapping("/medicalForm/{medicalFormSerial}")
    public ResponseEntity<MedicalFormRes> getMedicalFormInfo(@PathVariable("medicalFormSerial") int medicalFormSerial) {
        MedicalFormRes medicalFormInfo = medicalFormService.getMedicalFormInfo(medicalFormSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_MEDICAL_FORM_INFO, medicalFormInfo), HttpStatus.OK);
    }


    /**
     * 문진표 수정
     * @param changeMedicalFormDto (문진표 시리얼, 문진표 이름, 날짜 ,시간 문진표 문항들)
     * @param authentication (Jwt 활용)
     * @return 정상:200 / 그 외 처리x
     */
    @PatchMapping("/medicalForm/update")
    public ResponseEntity<MedicalFormDto> updateMedicalForm(@RequestBody ChangeMedicalFormReq changeMedicalFormDto, Authentication authentication) {
        Member findMember = extractMember(authentication);
        medicalFormService.updateMedicalForm(changeMedicalFormDto, findMember);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_UPDATE_MEDICAL_FORM), HttpStatus.OK);
    }


    /**
     * 문진표 삭제
     * @param medicalFormSerial
     * @return 정상:200 / 그 외 처리x
     */
    @DeleteMapping("/medicalForm/delete/{medicalFormSerial}")
    public ResponseEntity<MedicalFormDto> updateMedicalForm(@PathVariable("medicalFormSerial") int medicalFormSerial) {
        medicalFormService.deleteMedicalForm(medicalFormSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE_MEDICAL_FORM), HttpStatus.OK);
    }



    // Jwt 토큰으로 멤버 객체 반환
    private Member extractMember(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        Member findMember = memberRepository.findByMemberEmail(memberEmail);
        return findMember;
    }
}
