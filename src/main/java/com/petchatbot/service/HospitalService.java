package com.petchatbot.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.petchatbot.domain.dto.*;
import com.petchatbot.domain.model.*;
import com.petchatbot.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ObjectMessage;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

//    @Value("cloud.aws.region")
//    private String region;

    private AmazonS3 amazonS3;



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
    public void hospitalApply(HospitalApplyDto apply, String memberEmail) throws MessagingException, ParseException, IOException {
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
        boolean apptBill;

        // 비용정보 받는지 여부
        if (apply.getApptBill().equals("true")) apptBill=true;
        else apptBill=false;

        String apptReason = apply.getApptReason();

        // 상담 신청 저장
        MultipartFile imageFile = apply.getApptImage();
        //String s3ImageName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        int randomNumber = (int)(Math.random()*100000);
        char randomChar = (char) ((int) (Math.random() * 26) + 65);
        char randomChar2 = (char) ((int) (Math.random() * 26) + 97);

        String s3ImageName =  randomChar+randomNumber+randomChar2+imageFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(imageFile.getInputStream().available());

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion("ap-northeast-2")
                .build();

        amazonS3.putObject(bucket, s3ImageName, imageFile.getInputStream(), objMeta); // s3에 저장
        Appointment appointment = new Appointment(apptPet, apptMember, apptMedicalForm, apptPartner, apptDate, apptTime, apptMemberName, apptMemberTel, apptBill, apptReason, s3ImageName);

        if (s3ImageName.length()>50){
            log.info("파일명이 너무 김");
        }
        else appointmentRepository.save(appointment);


        // 연계병원에 이메일 전송
        emailService.sendEmailToHospital(emailDto, pnrName + "님에게 온 상담신청입니다" + " - 멍냥챗봇", apply, petSerial, medicalFormSerial, amazonS3,s3ImageName);


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
