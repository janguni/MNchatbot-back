package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.*;
import com.petchatbot.domain.model.Hospital;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.PetReq;
import com.petchatbot.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HospitalController {
    private final HospitalService hospitalService;
    @GetMapping("/hospital/{region}/{city}")
    public ResponseEntity<HospitalDto> searchTotalHospitalList(@PathVariable("region") String region,
                                                         @PathVariable("city") String city
                                                         ) {
        List<HospitalDto> totalHospitalList = hospitalService.searchTotalHospitalList(region, city);
        if (totalHospitalList.isEmpty())
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_HOSPITAL_LIST), HttpStatus.OK);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_HOSPITAL_LIST, totalHospitalList), HttpStatus.OK);
    }

    @RequestMapping(path = "/hospital/apply", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity applyAppointmentToHospital(@ModelAttribute HospitalApplyDto hospitalApplyDto, Authentication authentication) {
        try {
            hospitalService.hospitalApply(hospitalApplyDto);
            log.info("상담신청 완료!={}", hospitalApplyDto.getApptMemberName());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_APPLY_TO_HOSPITAL), HttpStatus.OK);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}




