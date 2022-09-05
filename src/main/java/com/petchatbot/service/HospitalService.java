package com.petchatbot.service;

import com.petchatbot.domain.model.Hospital;
import com.petchatbot.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public List<Hospital> searchHospitalList(String region, String city, String street){
        List<Hospital> byHospitalRegion = hospitalRepository.findByHospRegionAndHospCityAndHospStreet(region, city, street);
        return byHospitalRegion;
    }

}
