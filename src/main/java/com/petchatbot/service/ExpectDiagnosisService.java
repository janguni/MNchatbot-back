package com.petchatbot.service;

import com.petchatbot.domain.dto.DiseaseDto;
import com.petchatbot.domain.model.ExpectDiagnosis;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ExpectDiagInfoRes;
import com.petchatbot.domain.requestAndResponse.ExpectDiagListRes;
import com.petchatbot.repository.ExpectDiagnosisRepository;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpectDiagnosisService {

    private final PetRepository petRepository;
    private final ExpectDiagnosisRepository expectDiagRepository;

    private final DiseasesService diseasesService;
    private final MemberRepository memberRepository;

    public void addExpectDiag(int petSerial, String memberEmail, String diagnosisName){
        Date date = new Date();
        Time time = new Time(date.getTime());

        Member findMember = memberRepository.findByMemberEmail(memberEmail);
        Pet findPet = petRepository.findByPetSerial(petSerial);
        ExpectDiagnosis expectDiagnosis = new ExpectDiagnosis(findPet, findMember, date, time, diagnosisName);
        expectDiagRepository.save(expectDiagnosis);
    }


    // 예상진단 목록
    public List<ExpectDiagListRes> getExpectDiagList(int petSerial){

        List<ExpectDiagListRes> list = new ArrayList<>();

        Pet findPet = petRepository.findByPetSerial(petSerial);
        List<ExpectDiagnosis> expectDiagList = expectDiagRepository.findByPet(findPet);
        for (ExpectDiagnosis ed:expectDiagList) {
            int diagSerial = ed.getDiagSerial();
            String diagDsName = ed.getDiagDsName();
            Date diagDate = ed.getDiagDate();

            // 날짜 포맷 변경및 데이터 형식 String으로
            String newDiagDate = formatDate(diagDate,".");

            ExpectDiagListRes expectDiagListRes = new ExpectDiagListRes(diagSerial, diagDsName, newDiagDate);
            list.add(expectDiagListRes);
        }

        return list;
    }

    private String formatDate(Date diagDate, String separator) {
        SimpleDateFormat newDtFormat;

        if (separator.equals(".")) {
            newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
        }
        else {
            newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
        }

        String newDiagDate = newDtFormat.format(diagDate);
        return newDiagDate;
    }

    // 예상진단 세부 정보
    // 상담일자, 상담시간, 예상 질병명, 축종, 정의, 원인, 조언
    public ExpectDiagInfoRes getExpectDiagInfo(int diagSerial){
        ExpectDiagnosis findExpectDiag = expectDiagRepository.findByDiagSerial(diagSerial);
        String date = formatDate(findExpectDiag.getDiagDate(),"-");
        Time time = findExpectDiag.getDiagTime();
        int hours = time.getHours();
        int minutes = time.getMinutes();
        String formattedTime = hours + ":" + minutes;

        String diseaseName = findExpectDiag.getDiagDsName();
        DiseaseDto diseaseDto = diseasesService.getDiseaseInfoByDiseaseName(diseaseName);
        String breed = diseaseDto.getDsAmlBreed();
        String definition = diseaseDto.getDsDefinition();
        String cause = diseaseDto.getDsCause();
        String advice = diseaseDto.getDsAdvice();

        ExpectDiagInfoRes expectDiagInfoRes = new ExpectDiagInfoRes(date, formattedTime, diseaseName, breed, definition, cause, advice);
        return expectDiagInfoRes;

    }

    // 예상 진단 삭제
    public void deleteExpectDiag(int diagSerial){
        ExpectDiagnosis findExpectDiag = expectDiagRepository.findByDiagSerial(diagSerial);
        expectDiagRepository.delete(findExpectDiag);
    }
}
