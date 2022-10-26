package com.petchatbot.service;

import com.petchatbot.domain.dto.DiseaseDto;
import com.petchatbot.domain.model.Disease;
import com.petchatbot.domain.model.ExpectDiagnosis;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ExpectDiagInfoRes;
import com.petchatbot.domain.requestAndResponse.ExpectDiagListRes;
import com.petchatbot.repository.DiseasesRepository;
import com.petchatbot.repository.ExpectDiagnosisRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpectDiagnosisService {
    private final PetRepository petRepository;
    private final ExpectDiagnosisRepository expectDiagRepository;
    private final DiseasesRepository diseasesRepository;


    /**
     * 예상진단 전체 목록
     * @param petSerial
     * @return 해당 펫에 대한 전체 예상진단의 일부항목 (id, 예상질병명, 날짜)
     */
    public List<ExpectDiagListRes> getExpectDiagnoses(int petSerial){

        List<ExpectDiagListRes> expectDiagnosesRes = new ArrayList<>();
        List<ExpectDiagnosis> expectDiagnosis = getExpectDiagnosis(petSerial);

        for (ExpectDiagnosis ed:expectDiagnosis) {
            int diagSerial = ed.getDiagSerial();
            String diagDsName = ed.getDiagDsName();
            String diagDate = formatDate(ed);
            ExpectDiagListRes expectDiagListRes = new ExpectDiagListRes(diagSerial, diagDsName, diagDate);
            expectDiagnosesRes.add(expectDiagListRes);
        }

        return expectDiagnosesRes;
    }

    // Convert Date -> String (yyyy.MM.dd)
    private String formatDate(ExpectDiagnosis ed) {
        Date diagDate = ed.getDiagDate();
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = newDtFormat.format(diagDate);
        return formattedDate;
    }

    private List<ExpectDiagnosis> getExpectDiagnosis(int petSerial) {
        Pet findPet = petRepository.findByPetSerial(petSerial);
        List<ExpectDiagnosis> expectDiagList = expectDiagRepository.findByPet(findPet);
        return expectDiagList;
    }

    /**
     * 예상진단 세부 정보
     * @param diagSerial
     * @return 해당 예상진단의 세부정보 (날짜, 시간, 예상질병명, 질병 축종, 질병 정의, 질병 원인, 보호자 조언)
     */
   public ExpectDiagInfoRes getExpectDiagnosisInfo(int diagSerial){
       ExpectDiagnosis findExpectDiag = expectDiagRepository.findByDiagSerial(diagSerial);
       ExpectDiagInfoRes expectDiagInfoRes = getExpectDiagnosisInfo(findExpectDiag);
       return expectDiagInfoRes;
    }

    private ExpectDiagInfoRes getExpectDiagnosisInfo(ExpectDiagnosis ed) {
        Date edDate = ed.getDiagDate();
        String edTime = formatTime(ed);
        String edDsName = ed.getDiagDsName();
        Disease findDisease = diseasesRepository.findByDsName(edDsName);
        String edBreed = findDisease.getDsAmlBreed();
        String edDefinition = findDisease.getDsDefinition();
        String edCause = findDisease.getDsCause();
        String edAdvice = findDisease.getDsAdvice();

        ExpectDiagInfoRes expectDiagInfo = new ExpectDiagInfoRes(edDate, edTime, edDsName, edBreed, edDefinition, edCause, edAdvice);
        return expectDiagInfo;
    }

    // Convert Time -> String (hh:mm)
    private String formatTime(ExpectDiagnosis ed) {
        Time time = ed.getDiagTime();
        String formattedTime = time.getHours() + ":" + time.getMinutes();
        return formattedTime;
    }

    /**
     * 예상진단 삭제
     */
    public void deleteExpectDiagnosis(int diagSerial){
        ExpectDiagnosis findExpectDiag = expectDiagRepository.findByDiagSerial(diagSerial);
        expectDiagRepository.delete(findExpectDiag);
    }
}
