package com.petchatbot.service;

import com.petchatbot.domain.dto.MedicalFormDto;
import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.repository.MedicalFormRepository;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalFormService {

    private final MedicalFormRepository medicalFormRepository;
    private final PetRepository petRepository;
    private final MemberRepository memberRepository;


    public void saveMedicalForm(MedicalFormDto medicalFormDto, Member member) throws ParseException {
        int petSerial = medicalFormDto.getPetSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);

        String medicalFormName = medicalFormDto.getMedicalFormName();
        Date medicalFormDate = medicalFormDto.getMedicalFormDate();
        String medicalFormTime = medicalFormDto.getMedicalFormTime();
        String medicalFormQ1 = medicalFormDto.getMedicalFormQ1();
        String medicalFormQ2 = extractEnglishFirstLetter(medicalFormDto.getMedicalFormQ2());
        boolean medicalFormQ3 = medicalFormDto.isMedicalFormQ3();
        String medicalFormQ4 = medicalFormDto.getMedicalFormQ4();
        boolean medicalFormQ5 = medicalFormDto.isMedicalFormQ5();
        boolean medicalFormQ6 = medicalFormDto.isMedicalFormQ6();
        String medicalFormQ7 = medicalFormDto.getMedicalFormQ7();

        MedicalForm medicalForm = new MedicalForm(findPet, member, medicalFormName, medicalFormDate, medicalFormTime, medicalFormQ1, medicalFormQ2, medicalFormQ3, medicalFormQ4, medicalFormQ5, medicalFormQ6, medicalFormQ7);

        medicalFormRepository.save(medicalForm);
    }

    private String extractEnglishFirstLetter(String word){
        if (word.equals("내분비질환")) return "I";
        else if (word.equals("피부질환")) return "S";
        else if (word.equals("근골격계질환")) return "M";
        else if (word.equals("순환기질환")) return "C";
        else return null;
    }
}
