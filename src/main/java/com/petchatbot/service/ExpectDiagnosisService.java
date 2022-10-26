package com.petchatbot.service;

import com.petchatbot.domain.dto.DiseaseDto;
import com.petchatbot.domain.model.ExpectDiagnosis;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ExpectDiagInfoRes;
import com.petchatbot.domain.requestAndResponse.ExpectDiagListRes;
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

    private final DiseasesService diseasesService;

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
            SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
            String newDiagDate = newDtFormat.format(diagDate);

            ExpectDiagListRes expectDiagListRes = new ExpectDiagListRes(diagSerial, diagDsName, newDiagDate);
            list.add(expectDiagListRes);
        }

        return list;
    }

    // 예상진단 세부 정보
    // 상담일자, 상담시간, 예상 질병명, 축종, 정의, 원인, 조언
    public ExpectDiagInfoRes getExpectDiagInfo(int diagSerial){
        ExpectDiagnosis findExpectDiag = expectDiagRepository.findByDiagSerial(diagSerial);
        Date date = findExpectDiag.getDiagDate();
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
