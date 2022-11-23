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

    /**
     * 문진표 저장
     * @param medicalSaveReq
     * @param member (해당 문진표 저장 시 사용자 데이터 필요)
     */
    public void saveMedicalForm(MedicalForm.SaveReq medicalSaveReq, Member member){
        MedicalForm medicalForm = getMedicalForm(medicalSaveReq, member);
        medicalFormRepository.save(medicalForm);
    }


    /**
     * 문진표 수정
     * @param medicalEditReq
     */
    @Transactional
    public void updateMedicalForm(MedicalForm.EditReq medicalEditReq){

        // 수정할 문진표 객체 찾기
        int medicalSerial = medicalEditReq.getMedicalFormSerial();
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalSerial);


        // 약에 대한 특이사항이 없다면 질문 4번을 "특이사항 없음"으로 처리
        if (!medicalEditReq.isMedicalFormQ3()) {
            medicalEditReq.setMedicalFormQ4("특이사항 없음");
        }

        // 기타 특이사항이 없다면 질문 7번을 "특이사항 없음"으로 처리
        if (medicalEditReq.getMedicalFormQ7()==null){
            medicalEditReq.setMedicalFormQ7("특이사항 없음");
        }

        findMedicalForm.changeMedicalForm(medicalEditReq);
    }


    /**
     * 문진표 목록 확인
     * @param petSerial
     * @return List<MedicalForm.GroupRes>
     */
    public List<MedicalForm.GroupRes> getMedicalFormList(int petSerial){

        // 펫 시리얼로 문진표 목록 가져오기
        Pet findPet = petRepository.findByPetSerial(petSerial);
        List<MedicalForm> medicalForms = medicalFormRepository.findByPet(findPet);

        // 문진표들의 시리얼, 이름, 날짜를 추출하여 리스트에 담기
        List<MedicalForm.GroupRes> medicalFormGroup = new ArrayList<>();
        for (MedicalForm m : medicalForms){

            int serial = m.getMedicalFormSerial();
            String name = m.getMedicalFormName();
            Date date = m.getMedicalFormDate();

            // 날짜 형식을 yyyy.mm.dd로 변환
            SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
            String newDate = newDtFormat.format(date);

            MedicalForm.GroupRes group = new MedicalForm.GroupRes(serial, name, newDate);
            medicalFormGroup.add(group);
        }

        return medicalFormGroup;
    }


    /**
     * 문진표 세부 정보 확인
     * @param medicalFormSerial
     * @return MedicalForm.DetailRes
     */
    public MedicalForm.DetailRes getMedicalFormInfo(int medicalFormSerial){
        // 문진표 시리얼로 문진표 찾기
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);

        // 문진표 객체를 response 형태에 맞는 객체로 변환
        MedicalForm.DetailRes medicalDetail = getMedicalDetailRes(findMedicalForm);

        return medicalDetail;
    }




    // 문진표 삭제
    public void deleteMedicalForm(int medicalFormSerial){
        // 문진표 시리얼로 문진표 찾기
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);

        medicalFormRepository.delete(findMedicalForm);
    }


    // 문진표저장요청 객체로 문진표 객체 생성해서 반환
    private MedicalForm getMedicalForm(MedicalForm.SaveReq medicalSaveReq, Member member) {

        // 펫 시리얼로 펫 찾기
        int petSerial = medicalSaveReq.getPetSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);

        String medicalFormName = medicalSaveReq.getMedicalFormName();
        Date medicalFormDate = medicalSaveReq.getMedicalFormDate();
        String medicalFormTime = medicalSaveReq.getMedicalFormTime();
        String medicalFormQ1 = medicalSaveReq.getMedicalFormQ1();
        String medicalFormQ2 = extractEnglishFirstLetter(medicalSaveReq.getMedicalFormQ2());
        boolean medicalFormQ3 = medicalSaveReq.isMedicalFormQ3();

        // 약에 대한 특이사항이 없다면 질문 4번을 "특이사항 없음"으로 처리
        String medicalFormQ4;
        if(medicalFormQ3) medicalFormQ4 = medicalSaveReq.getMedicalFormQ4();
        else medicalFormQ4 = "특이사항 없음";

        boolean medicalFormQ5 = medicalSaveReq.isMedicalFormQ5();
        boolean medicalFormQ6 = medicalSaveReq.isMedicalFormQ6();

        // 기타 특이사항이 없다면 질문 7번을 "특이사항 없음"으로 처리
        String medicalFormQ7;
        if (medicalSaveReq.getMedicalFormQ7()==null) medicalFormQ7 = "특이사항 없음";
        else medicalFormQ7 = medicalSaveReq.getMedicalFormQ7();

        // 문진표 객체 생성
        MedicalForm medicalForm = new MedicalForm(findPet, member, medicalFormName, medicalFormDate, medicalFormTime, medicalFormQ1, medicalFormQ2, medicalFormQ3, medicalFormQ4, medicalFormQ5, medicalFormQ6, medicalFormQ7);

        return medicalForm;
    }

    // 문진표 객체로 문진표 세부정보객체를 생성해서 반환
    private MedicalForm.DetailRes getMedicalDetailRes(MedicalForm findMedicalForm) {

        String name = findMedicalForm.getMedicalFormName();
        Date date = findMedicalForm.getMedicalFormDate();

        // 날짜 형식을 yyyy-mm-dd로 변환
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = newDtFormat.format(date);

        String time = findMedicalForm.getMedicalFormTime();
        String q1 = findMedicalForm.getMedicalFormQ1();
        String q2 = findMedicalFormQ2ByLetter(findMedicalForm.getMedicalFormQ2());
        boolean q3 = findMedicalForm.isMedicalFormQ3();
        String q4 = findMedicalForm.getMedicalFormQ4();
        boolean q5 = findMedicalForm.isMedicalFormQ5();
        boolean q6 = findMedicalForm.isMedicalFormQ6();
        String q7 = findMedicalForm.getMedicalFormQ7();

        MedicalForm.DetailRes medicalDetail = new MedicalForm.DetailRes(name, newDate, time, q1, q2, q3, q4, q5, q6, q7);
        return medicalDetail;
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
