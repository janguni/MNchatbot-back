package com.petchatbot.service;

import com.petchatbot.domain.dto.HospitalDto;
import com.petchatbot.domain.model.Hospital;
import com.petchatbot.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public List<HospitalDto> searchHospitalList(String region, String city, String street){
        List<Hospital> hospitalList = hospitalRepository.findByHospRegionAndHospCityAndHospStreet(region, city, street);

        List<HospitalDto> hospitals = new ArrayList<>();

        for (Hospital hospital : hospitalList){
            String hospName = hospital.getHospName();
            String hospAddress = hospital.getHospAddress();
            String hospTel = hospital.getHospTel();
            HospitalDto hospitalDto = new HospitalDto(hospName, hospAddress, hospTel);
            hospitals.add(hospitalDto);
        }
        return hospitals;
    }

}
