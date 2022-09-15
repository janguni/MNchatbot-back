package com.petchatbot.service;

import com.petchatbot.domain.dto.HospitalDto;
import com.petchatbot.domain.dto.PartnerDto;
import com.petchatbot.domain.dto.TotalHospitalDto;
import com.petchatbot.domain.model.Hospital;
import com.petchatbot.domain.model.HospitalType;
import com.petchatbot.domain.model.Partner;
import com.petchatbot.repository.HospitalRepository;
import com.petchatbot.repository.PartnerRepository;
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
    private final PartnerRepository partnerRepository;

    public List<TotalHospitalDto> searchTotalHospitalList(String region, String city){

        List<TotalHospitalDto> totalHospitals = new ArrayList<>();

        List<HospitalDto> hospitals = searchHospitalList(region, city);
        List<PartnerDto> partners = searchPartnerList(region, city);

        for (HospitalDto hospital : hospitals){
            String hospName = hospital.getHospName();
            String hospAddress = hospital.getHospAddress();
            String hospTel = hospital.getHospTel();
            TotalHospitalDto hospitalDto = new TotalHospitalDto(hospName, hospAddress, hospTel, HospitalType.NOPARTNER);
            totalHospitals.add(hospitalDto);
        }

        for (PartnerDto partner : partners){
            String pnrName = partner.getPnrName();
            String pnrAddress = partner.getPnrAddress();
            String pnrEmail = partner.getPnrEmail();
            String pnrTel = partner.getPnrTel();
            String pnrField = partner.getPnrField();
            TotalHospitalDto totalHospitalDto = new TotalHospitalDto(pnrName, pnrAddress, pnrEmail, pnrTel, pnrField,HospitalType.PARTNER);
            totalHospitals.add(totalHospitalDto);
        }

        return totalHospitals;
    }


    // 주위 동물병원 검색해서 동물병원 리스트 보기
    private List<HospitalDto> searchHospitalList(String region, String city){
        List<Hospital> hospitalList = hospitalRepository.findByHospRegionAndHospCity(region, city);

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

    // 주위 연계병원 보기
    private List<PartnerDto> searchPartnerList(String region, String city){
        List<Partner> partnerList = partnerRepository.findByPnrRegionAndPnrCity(region, city);

        List<PartnerDto> partners = new ArrayList<>();

        for (Partner partner : partnerList){
            String pnrName = partner.getPnrName();
            String pnrTel = partner.getPnrTel();
            String pnrEmail = partner.getPnrEmail();
            String pnrAddress = partner.getPnrAddress();
            String pnrField = partner.getPnrField();
            PartnerDto partnerDto = new PartnerDto(pnrName, pnrTel, pnrEmail, pnrAddress, pnrField);
            partners.add(partnerDto);

        }

        return partners;
    }



}
