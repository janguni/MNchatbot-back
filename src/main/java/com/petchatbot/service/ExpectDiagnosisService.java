package com.petchatbot.service;

import com.petchatbot.domain.model.ExpectDiagnosis;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ExpectDiagListRes;
import com.petchatbot.repository.ExpectDiagnosisRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    // 예상 진단 삭제
    public void deleteExpectDiag(int diagSerial){
        ExpectDiagnosis findExpectDiag = expectDiagRepository.findByDiagSerial(diagSerial);
        expectDiagRepository.delete(findExpectDiag);
    }
}
