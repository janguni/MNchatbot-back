package com.petchatbot.service;

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

//    public DiseaseDto getDiseaseInfo(String dsId){
//        Optional<Disease> findDisease = diseasesRepository.findById(dsId);
//        Disease findDisease1 = (Disease) findDisease;
//
//
//    }


}
