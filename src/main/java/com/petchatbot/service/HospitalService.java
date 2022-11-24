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
    private AmazonS3 amazonS3;


    /**
     * 동물병원 검색
     * @param region (시도)
     * @param city (시군구)
     * @return List<HospitalDto>
     */
    public List<HospitalDto> searchTotalHospitalList(String region, String city){

        List<HospitalDto> totalHospitals = new ArrayList<>(); // 전체 동물병원을 담을 list 선언
        List<HospitalDto> hospitals = searchHospitalList(region, city); // 검색조건에 해당된 동물병원
        List<HospitalDto> partners = searchPartnerList(region, city); // 검색조건에 해당된 연계병원

        // 전체 동물변원에 담기
        for (HospitalDto hospital : hospitals){
            totalHospitals.add(hospital);
        }
        for (HospitalDto hospital : partners){
            totalHospitals.add(hospital);
        }

        return totalHospitals;
    }


    /**
     * 상담신청
     * @param apply
     * @param memberEmail
     * @throws MessagingException
     * @throws ParseException
     * @throws IOException
     */
    public void hospitalApply(HospitalApplyDto apply, String memberEmail) throws MessagingException, ParseException, IOException {
        // 사용자 객체 찾기
        Member apptMember = memberRepository.findByMemberEmail(memberEmail);

        // 펫 객체 찾기
        int petSerial = Integer.parseInt(apply.getPetSerial());
        Pet apptPet = petRepository.findByPetSerial(petSerial);

        // 연게병원 객체 찾기
        int partnerSerial = Integer.parseInt(apply.getPartnerSerial());
        Partner apptPartner = partnerRepository.findByPnrSerial(partnerSerial);

        // 문진표 객체 찾기
        int medicalFormSerial = Integer.parseInt(apply.getMedicalSerial());
        MedicalForm apptMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);


        String pnrEmail = apptPartner.getPnrEmail();
        EmailDto emailDto = new EmailDto(pnrEmail); // 연계병원 이메일

        String pnrName = apptPartner.getPnrName(); // 연계병원 이름

        // 상담 날짜 formatting
        String date = apply.getApptDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date apptDate = dateFormat.parse(date);

        String apptTime = apply.getApptTime(); // 상담 시간
        String apptMemberName = apply.getApptMemberName(); // 상담 신청인 이름
        String apptMemberTel = apply.getApptMemberTel(); // 상담 신청인 번호

        // 비용정보 받는지 여부
        boolean apptBill;
        if (apply.getApptBill().equals("true")) apptBill=true;
        else apptBill=false;

        String apptReason = apply.getApptReason(); //상담 신청 이유

        MultipartFile imageFile = apply.getApptImage(); // 반려동물 사진


        String s3ImageName;
        //String s3ImageName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        if (imageFile ==null) s3ImageName = "noImageFile";
        else s3ImageName= saveImageToS3(imageFile);

        // 상담 신청 저장
        Appointment appointment = new Appointment(apptPet, apptMember, apptMedicalForm, apptPartner, apptDate, apptTime, apptMemberName, apptMemberTel, apptBill, apptReason, s3ImageName);
        appointmentRepository.save(appointment);

        // 연계병원에 이메일 전송
        emailService.sendEmailToHospital(emailDto, pnrName + "님에게 온 상담신청입니다" + " - 멍냥챗봇", apply, petSerial, medicalFormSerial, amazonS3, s3ImageName);
    }

    private String saveImageToS3(MultipartFile imageFile) throws IOException {

        String s3ImageName;

        // 랜덤으로 숫자와 문자를 추출하여 이미지 파일 제목 설정
        int randomNumber = (int)(Math.random()*100000); // 랜덤으로 0 ~ 100000 사이의 숫자 추출
        char randomChar = (char) ((int) (Math.random() * 26) + 65); // 랜덤으로 대문자 추출
        char randomChar2 = (char) ((int) (Math.random() * 26) + 97); // 랜덤으로 소문자 추출
        s3ImageName =  randomChar+randomNumber+randomChar2+ imageFile.getOriginalFilename();


        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(imageFile.getInputStream().available());
        amazonS3 = AmazonS3ClientBuilder.standard() // s3 불러오기?
                .withRegion("ap-northeast-2")
                .build();

        amazonS3.putObject(bucket, s3ImageName, imageFile.getInputStream(), objMeta); // s3에 저장

        if (s3ImageName.length()>50){ // 파일명이 길 경우
            log.info("파일명이 너무 김"); // test 코드 작성 이후 수정 예정 (2022.11.24)
        }

        return s3ImageName;
    }


    // 주위 동물병원 검색 후 반환
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

    // 주위 연계병원 검색 후 반환
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
