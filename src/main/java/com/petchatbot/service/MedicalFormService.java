package com.petchatbot.service;

import com.petchatbot.domain.dto.MedicalFormDto;
import com.petchatbot.domain.dto.MedicalFormListDto;
import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ChangeMedicalFormReq;
import com.petchatbot.domain.requestAndResponse.MedicalFormRes;
import com.petchatbot.repository.MedicalFormRepository;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalFormService {

    private final MedicalFormRepository medicalFormRepository;
    private final PetRepository petRepository;

    // 문진표 저장
    public void saveMedicalForm(MedicalFormDto medicalFormDto, Member member){
        int petSerial = medicalFormDto.getPetSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);

        String medicalFormName = medicalFormDto.getMedicalFormName();
        Date medicalFormDate = medicalFormDto.getMedicalFormDate();
        String medicalFormTime = medicalFormDto.getMedicalFormTime();
        String medicalFormQ1 = medicalFormDto.getMedicalFormQ1();
        String medicalFormQ2 = extractEnglishFirstLetter(medicalFormDto.getMedicalFormQ2());
        boolean medicalFormQ3 = medicalFormDto.isMedicalFormQ3();
        String medicalFormQ4;
        if(medicalFormQ3) medicalFormQ4 = medicalFormDto.getMedicalFormQ4();
        else medicalFormQ4 = "특이사항 없음";

        boolean medicalFormQ5 = medicalFormDto.isMedicalFormQ5();
        boolean medicalFormQ6 = medicalFormDto.isMedicalFormQ6();
        String medicalFormQ7;
        log.info("medicalFormQ7={}",medicalFormDto.getMedicalFormQ7());
        if (medicalFormDto.getMedicalFormQ7()==null){
            medicalFormQ7 = "특이사항 없음";
        }
        else medicalFormQ7 = medicalFormDto.getMedicalFormQ7();

        MedicalForm medicalForm = new MedicalForm(findPet, member, medicalFormName, medicalFormDate, medicalFormTime, medicalFormQ1, medicalFormQ2, medicalFormQ3, medicalFormQ4, medicalFormQ5, medicalFormQ6, medicalFormQ7);

        medicalFormRepository.save(medicalForm);
    }
    // 문진표 수정
    @Transactional
    public void updateMedicalForm(ChangeMedicalFormReq medicalFormReq, Member member){
        int medicalSerial = medicalFormReq.getMedicalFormSerial();
        log.info("medicalFormSerial={}", medicalSerial);
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalSerial);
        String name = medicalFormReq.getMedicalFormName();
        //Date date = medicalFormReq.getMedicalFormDate();
        //String time = medicalFormReq.getMedicalFormTime();
        String q1 = medicalFormReq.getMedicalFormQ1();
        String q2 = extractEnglishFirstLetter(medicalFormReq.getMedicalFormQ2());
        boolean q3 = medicalFormReq.isMedicalFormQ3();
        String q4;
        if(q3) q4 = medicalFormReq.getMedicalFormQ4();
        else q4 = "특이사항 없음";
        boolean q5 = medicalFormReq.isMedicalFormQ5();
        boolean q6 = medicalFormReq.isMedicalFormQ6();
        String q7;
        if (medicalFormReq.getMedicalFormQ7()==null){
            q7 = "특이사항 없음";
        }
        else q7 = medicalFormReq.getMedicalFormQ7();
        findMedicalForm.changeMedicalForm(name,q1, q2, q3, q4, q5, q6, q7);
    }

    // 문진표 목록 불러오기
    public List<MedicalFormListDto> getMedicalFormList(int petSerial){
        List<MedicalFormListDto> medicalFormList = new ArrayList<>();
        Pet findPet = petRepository.findByPetSerial(petSerial);

        List<MedicalForm> medicalForms = medicalFormRepository.findByPet(findPet);
        for (MedicalForm medicalForm : medicalForms){
            int medicalFormSerial = medicalForm.getMedicalFormSerial();
            String medicalFormName = medicalForm.getMedicalFormName();
            Date medicalFormDate = medicalForm.getMedicalFormDate();

            SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
            String newMedicalFormDate = newDtFormat.format(medicalFormDate);

            MedicalFormListDto medicalFormListDto = new MedicalFormListDto(medicalFormSerial, medicalFormName, newMedicalFormDate);
            medicalFormList.add(medicalFormListDto);
        }

        return medicalFormList;
    }

    // 문진표 세부 정보
    public MedicalFormRes getMedicalFormInfo(int medicalFormSerial){
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);
        String medicalFormName = findMedicalForm.getMedicalFormName();
        Date medicalFormDate = findMedicalForm.getMedicalFormDate();
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newMedicalFormDate = newDtFormat.format(medicalFormDate);
        String medicalFormTime = findMedicalForm.getMedicalFormTime();
        String medicalFormQ1 = findMedicalForm.getMedicalFormQ1();
        String medicalFormQ2 = findMedicalFormQ2ByLetter(findMedicalForm.getMedicalFormQ2());
        boolean medicalFormQ3 = findMedicalForm.isMedicalFormQ3();
        String medicalFormQ4 = findMedicalForm.getMedicalFormQ4();
        boolean medicalFormQ5 = findMedicalForm.isMedicalFormQ5();
        boolean medicalFormQ6 = findMedicalForm.isMedicalFormQ6();
        String medicalFormQ7 = findMedicalForm.getMedicalFormQ7();

        MedicalFormRes medicalFormRes = new MedicalFormRes(medicalFormName, newMedicalFormDate, medicalFormTime, medicalFormQ1, medicalFormQ2, medicalFormQ3, medicalFormQ4, medicalFormQ5, medicalFormQ6, medicalFormQ7);
        return medicalFormRes;
    }

    // 문진표 삭제
    public void deleteMedicalForm(int medicalFormSerial){
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);
        medicalFormRepository.delete(findMedicalForm);
    }

    private String extractEnglishFirstLetter(String word){
        if (word.equals("내분비질환")) return "I";
        else if (word.equals("피부질환")) return "S";
        else if (word.equals("근골격계질환")) return "M";
        else if (word.equals("순환기질환")) return "C";
        else return "F";
    }

    private String findMedicalFormQ2ByLetter(String word){
        if (word.equals("I")) return "내분비질환";
        else if (word.equals("S")) return "피부질환";
        else if (word.equals("M")) return "근골격계질환";
        else if (word.equals("C")) return "순환기질환";
        else return "없음";
    }
}
