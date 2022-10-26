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


    /**
     * 질병명으로 검색
     * @param inputWord
     * @return 검색단어를 포함한 질병의 일부 정보(질병 id, 질병명)
     */
    public List<DiseaseListDto> searchDisease(String inputWord){
        String inputWordForQuery = ".*" + inputWord + ".*";
        List<Disease> diseases = diseasesRepository.findByDsNameRegex(inputWordForQuery);

        if (diseases==null){ // 검색결과 없음
            return null;
        }
        return getDiseasesRes(diseases);
    }

    // 질병들의 id, name 만을 전달
    private List<DiseaseListDto> getDiseasesRes(List<Disease> diseases){
        List<DiseaseListDto> diseasesRes = new ArrayList<>();
        for (Disease ds: diseases){
            String diseaseId = ds.getId();
            String diseaseName = ds.getDsName();
            DiseaseListDto diseaseDto = new DiseaseListDto(diseaseId, diseaseName);
            diseasesRes.add(diseaseDto);
        }
        return diseasesRes;
    }

    /**
     * 질병백과 페이지 열람
     * @param page
     * @param diseaseCnt
     * @return 질병들의 일부 정보(질병 id, 질병명)
     */
    public DiseaseDictionaryDto getDiseasesList(int page, int diseaseCnt) {

        List<DiseaseListDto> diseasesRes = new ArrayList<>();

        int startNumber = (page-1) * 10; // 질병 시작 갯수
        List<Disease> totalDisease = diseasesRepository.findAll();

        for (int i=startNumber; i<startNumber+diseaseCnt; i++){
            Disease disease = totalDisease.get(i);
            String diseaseId = disease.getId();
            String diseaseName = disease.getDsName();
            DiseaseListDto diseaseDto = new DiseaseListDto(diseaseId, diseaseName);
            diseasesRes.add(diseaseDto);
        }

        long totalCount = diseasesRepository.count();
        boolean hasNextPage =  startNumber+diseaseCnt < totalCount;

        DiseaseDictionaryDto diseaseDictionaryPageInfo = new DiseaseDictionaryDto(totalCount, hasNextPage, diseasesRes);
        return diseaseDictionaryPageInfo;
    }

    public DiseaseDto getDiseaseInfByDiseaseId(String diseaseId){
        Optional<Disease> findDisease = diseasesRepository.findById(diseaseId);
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
