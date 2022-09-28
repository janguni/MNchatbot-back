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
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalFormService {

    private final MedicalFormRepository medicalFormRepository;
    private final PetRepository petRepository;
    private final MemberRepository memberRepository;


    public void saveMedicalForm(MedicalFormDto medicalFormDto, Member member){
        int petSerial = medicalFormDto.getPetSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);

        String medicalFormName = medicalFormDto.getMedicalFormName();
        Date medicalFormDate = medicalFormDto.getMedicalFormDate();
        Time medicalFormTime = medicalFormDto.getMedicalFormTime();
        String medicalFormQ1 = medicalFormDto.getMedicalFormQ1();
        boolean medicalFormQ2 = medicalFormDto.isMedicalFormQ2();
        String medicalFormQ3 = medicalFormDto.getMedicalFormQ3();
        boolean medicalFormQ4 = medicalFormDto.isMedicalFormQ4();
        boolean medicalFormQ5 = medicalFormDto.isMedicalFormQ5();
        boolean medicalFormQ6 = medicalFormDto.isMedicalFormQ6();
        String medicalFormQ7 = medicalFormDto.getMedicalFormQ7();

        MedicalForm medicalForm = new MedicalForm(findPet, member, medicalFormName, medicalFormDate, medicalFormTime, medicalFormQ1, medicalFormQ2, medicalFormQ3, medicalFormQ4, medicalFormQ5, medicalFormQ6, medicalFormQ7);

        medicalFormRepository.save(medicalForm);
    }
}
