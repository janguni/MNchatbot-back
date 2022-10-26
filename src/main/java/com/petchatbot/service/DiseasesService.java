package com.petchatbot.service;

import com.petchatbot.domain.dto.DiseaseDictionaryDto;
import com.petchatbot.domain.dto.DiseaseDto;
import com.petchatbot.domain.dto.DiseaseListDto;
import com.petchatbot.domain.model.Disease;
import com.petchatbot.repository.DiseasesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class DiseasesService {

    private final DiseasesRepository diseasesRepository;

    public List<DiseaseListDto> searchDiseases(String inputDsName){
        String queryDsName = ".*" + inputDsName + ".*";
        List<Disease> dsList = diseasesRepository.findByDsNameRegex(queryDsName);

        if (dsList==null){ // 검색결과 없음
            return null;
        }

        List<DiseaseListDto> diseases = new ArrayList<>();
        for (Disease ds: dsList){
            String dsId = ds.getId();
            String dsName = ds.getDsName();
            DiseaseListDto diseaseListDto = new DiseaseListDto(dsId, dsName);
            diseases.add(diseaseListDto);
        }

        return diseases;
    }

    public DiseaseDictionaryDto getDiseasesList(int page, int itemCnt) {

        List<DiseaseListDto> diseases = new ArrayList<>();
        int startNumber = (page-1) * 10; // 질병 시작 갯수
        List<Disease> totalDisease = diseasesRepository.findAll(); // 전체 질병 정보 불러옴
        for (int i=startNumber; i<startNumber+itemCnt; i++){
            Disease disease = totalDisease.get(i);
            String dsId = disease.getId();
            String dsName = disease.getDsName();
            DiseaseListDto diseaseListDto = new DiseaseListDto(dsId, dsName);
            diseases.add(diseaseListDto);
        }

        long totalCount = diseasesRepository.count();


        DiseaseDictionaryDto miniDiseaseDictionary = new DiseaseDictionaryDto(totalCount,startNumber+itemCnt<totalCount, diseases);
        return miniDiseaseDictionary;
    }

    public DiseaseDto getDiseaseInfByDiseaseId(String dsId){
        Optional<Disease> findDisease = diseasesRepository.findById(dsId);
        Disease disease = findDisease.get();
        String dsName = disease.getDsName();
        String dsAmlBreed = disease.getDsAmlBreed();
        String dsDefinition = disease.getDsDefinition();
        String dsCause = disease.getDsCause();
        String dsPathogenesis = disease.getDsPathogenesis();
        String dsEpidemiology = disease.getDsEpidemiology();
        String dsSymptom = disease.getDsSymptom();
        String dsDiagnosis = disease.getDsDiagnosis();
        String dsTreatment = disease.getDsTreatment();
        String dsPrevention = disease.getDsPrevention();
        String dsPrognosis = disease.getDsPrognosis();
        String dsAdvice = disease.getDsAdvice();
        DiseaseDto diseaseDto = new DiseaseDto(dsName, dsAmlBreed, dsDefinition, dsCause, dsPathogenesis, dsEpidemiology, dsSymptom, dsDiagnosis, dsTreatment, dsPrevention, dsPrognosis, dsAdvice);
        return diseaseDto;
    }

    public DiseaseDto getDiseaseInfoByDiseaseName(String dsName){
        Disease findDisease = diseasesRepository.findByDsName(dsName);
        log.info("findDiseaseByDiseaseName={}", findDisease);
        String dsAmlBreed = findDisease.getDsAmlBreed();
        String dsDefinition = findDisease.getDsDefinition();
        String dsCause = findDisease.getDsCause();
        String dsPathogenesis = findDisease.getDsPathogenesis();
        String dsEpidemiology = findDisease.getDsEpidemiology();
        String dsSymptom = findDisease.getDsSymptom();
        String dsDiagnosis = findDisease.getDsDiagnosis();
        String dsTreatment = findDisease.getDsTreatment();
        String dsPrevention = findDisease.getDsPrevention();
        String dsPrognosis = findDisease.getDsPrognosis();
        String dsAdvice = findDisease.getDsAdvice();
        DiseaseDto diseaseDto = new DiseaseDto(dsName, dsAmlBreed, dsDefinition, dsCause, dsPathogenesis, dsEpidemiology, dsSymptom, dsDiagnosis, dsTreatment, dsPrevention, dsPrognosis, dsAdvice);
        return diseaseDto;
    }
}
