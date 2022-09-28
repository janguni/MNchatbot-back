package com.petchatbot.service;

import com.petchatbot.domain.dto.*;
import com.petchatbot.domain.model.Hospital;
import com.petchatbot.domain.model.HospitalType;
import com.petchatbot.domain.model.Partner;
import com.petchatbot.repository.HospitalRepository;
import com.petchatbot.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.petchatbot.domain.model.HospitalType.NOPARTNER;
import static com.petchatbot.domain.model.HospitalType.PARTNER;

@Service
@RequiredArgsConstructor
@Slf4j
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final PartnerRepository partnerRepository;
    private final EmailService emailService;


    // 동물병원, 연계병원 주소로 검색
    public List<HospitalDto> searchTotalHospitalList(String region, String city){

        List<HospitalDto> totalHospitals = new ArrayList<>();

        List<HospitalDto> hospitals = searchHospitalList(region, city);
        List<HospitalDto> partners = searchPartnerList(region, city);

        // 주위 동물병원 리스트에 add
        for (HospitalDto hospital : hospitals){
            totalHospitals.add(hospital);
        }

        // 연계병원 리스트에 add
        for (HospitalDto hospital : partners){
            totalHospitals.add(hospital);
        }

        return totalHospitals;
    }

    //상담신청
    public void hospitalApply(HospitalApplyDto apply) throws MessagingException {
        int partnerSerial = apply.getPartnerSerial();
        Partner findPartner = partnerRepository.findByPnrSerial(partnerSerial);
        String pnrEmail = findPartner.getPnrEmail();
        EmailDto emailDto = new EmailDto(pnrEmail);
        String pnrName = findPartner.getPnrName();

        emailService.sendEmailToHospital(emailDto, pnrName + "님에게 온 상담신청입니다" + " - 멍냥챗봇", apply);

    }



    // 주위 일반동물병원
    private List<HospitalDto> searchHospitalList(String region, String city){
        List<Hospital> hospitalList = hospitalRepository.findByHospRegionAndHospCity(region, city);

        List<HospitalDto> hospitals = new ArrayList<>();

        for (Hospital hospital : hospitalList){
            String hospName = hospital.getHospName();
            String hospAddress = hospital.getHospAddress();
            String hospTel = hospital.getHospTel();
            HospitalDto hospitalDto = new HospitalDto(hospName, hospAddress, hospTel, NOPARTNER);
            hospitals.add(hospitalDto);
        }
        return hospitals;
    }

    // 주위 연계병원
    private List<HospitalDto> searchPartnerList(String region, String city){
        List<Partner> partnerList = partnerRepository.findByPnrRegionAndPnrCity(region, city);

        List<HospitalDto> partners = new ArrayList<>();

        for (Partner partner : partnerList){
            String pnrName = partner.getPnrName();
            String pnrTel = partner.getPnrTel();
            String pnrEmail = partner.getPnrEmail();
            String pnrAddress = partner.getPnrAddress();
            String pnrField = partner.getPnrField();
            int pnrSerial = partner.getPnrSerial();
            HospitalDto partnerDto = new HospitalDto(pnrSerial, pnrName, pnrAddress, pnrTel, PARTNER);
            partners.add(partnerDto);
        }

        return partners;
    }



}
