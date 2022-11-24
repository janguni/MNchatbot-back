package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.*;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


// 1. 해당 시도/시군구 동물병원 검색
// 2. 연계병원에 상담신청

@RestController
@RequiredArgsConstructor
@Slf4j
public class HospitalController {
    private final HospitalService hospitalService;

    /**
     * 해당 시도/시군구 동물병원 검색
     * @param region (시도)
     * @param city (시군구)
     * @return 정상:200  / 검색한 지역의 동물병원이 없을 시: 404
     */
    @GetMapping("/hospital/{region}/{city}")
    public ResponseEntity<HospitalDto> searchTotalHospitalList(@PathVariable("region") String region,
                                                         @PathVariable("city") String city) {
        List<HospitalDto> totalHospitalList = hospitalService.searchTotalHospitalList(region, city);

        if (totalHospitalList.isEmpty()) // 검색한 지역의 동물병원이 없을 시
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_HOSPITAL_LIST), HttpStatus.OK);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_HOSPITAL_LIST, totalHospitalList), HttpStatus.OK);
    }

    /**
     * 연계병원에 상담신청
     * @param hospitalApplyDto (펫 시리얼, 문진표 시리얼, 연계병원 시리얼, 날짜, 시간, ...)
     * @param authentication (Jwt 토큰 활용)
     * @return 정상:200 / 그 외 오류가 날 시에는 예외를 터트림
     */
    @RequestMapping(path = "/hospital/apply", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity applyAppointmentToHospital(@ModelAttribute HospitalApplyDto hospitalApplyDto, Authentication authentication) {
        try {
            String memberEmail = extractEmail(authentication);
            hospitalService.hospitalApply(hospitalApplyDto, memberEmail);
            log.info("상담신청 완료!={}", hospitalApplyDto.getApptMemberName());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_APPLY_TO_HOSPITAL), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // jwt 토큰에서 사용자 이메일 추출하여 반환
    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}




