package com.petchatbot.service;

import com.amazonaws.services.s3.AmazonS3;
import com.petchatbot.domain.dto.*;
import com.petchatbot.domain.model.*;
import com.petchatbot.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final AppointmentRepository appointmentRepository;

    private final MemberRepository memberRepository;

    private final PetRepository petRepository;

    private final MedicalFormRepository medicalFormRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;



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
    public void hospitalApply(HospitalApplyDto apply, String memberEmail) throws MessagingException, ParseException {
        Member apptMember = memberRepository.findByMemberEmail(memberEmail);
        int petSerial = Integer.parseInt(apply.getPetSerial());
        Pet apptPet = petRepository.findByPetSerial(petSerial);
        int partnerSerial = Integer.parseInt(apply.getPartnerSerial());
        Partner apptPartner = partnerRepository.findByPnrSerial(partnerSerial);
        int medicalFormSerial = Integer.parseInt(apply.getMedicalSerial());
        MedicalForm apptMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);
        String pnrEmail = apptPartner.getPnrEmail();
        EmailDto emailDto = new EmailDto(pnrEmail);
        String pnrName = apptPartner.getPnrName();

        String date = apply.getApptDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date apptDate = dateFormat.parse(date);
        String apptTime = apply.getApptTime();
        String apptMemberName = apply.getApptMemberName();
        String apptMemberTel = apply.getApptMemberTel();
        String apptBill = apply.getApptBill();
        String apptReason = apply.getApptReason();

        // 연계병원에 이메일 전송
        emailService.sendEmailToHospital(emailDto, pnrName + "님에게 온 상담신청입니다" + " - 멍냥챗봇", apply, petSerial, medicalFormSerial);

        // 상담 신청 저장
        //new Appointment(pet, member, medicalForm, partner, )


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
            HospitalDto partnerDto = new HospitalDto(pnrSerial, pnrName, pnrAddress, pnrTel, pnrEmail,pnrField,PARTNER);
            partners.add(partnerDto);
        }

        return partners;
    }



}
