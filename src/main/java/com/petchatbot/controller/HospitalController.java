package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.DiseaseDto;
import com.petchatbot.domain.dto.HospitalDto;
import com.petchatbot.domain.dto.PartnerDto;
import com.petchatbot.domain.dto.TotalHospitalDto;
import com.petchatbot.domain.model.Hospital;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/hospital/{region}/{city}")
    public ResponseEntity<HospitalDto> searchTotalHospitalList(@PathVariable("region") String region,
                                                         @PathVariable("city") String city
                                                         ) {
        List<TotalHospitalDto> totalHospitalList = hospitalService.searchTotalHospitalList(region, city);
        if (totalHospitalList.isEmpty())
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_HOSPITAL_LIST), HttpStatus.OK);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_HOSPITAL_LIST, totalHospitalList), HttpStatus.OK);
    }

//    @PostMapping("/hospital/apply")
//    public ResponseEntity<>
}




