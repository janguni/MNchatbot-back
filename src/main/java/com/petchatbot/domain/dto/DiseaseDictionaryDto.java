package com.petchatbot.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class DiseaseDictionaryDto {
    public long totalDiseaseCnt;
    public boolean hasNextPage;
    public List<DiseaseListDto> diseaseList;


    public DiseaseDictionaryDto(long totalDiseaseCnt, boolean hasNextPage, List<DiseaseListDto> diseaseList) {
        this.totalDiseaseCnt = totalDiseaseCnt;
        this.hasNextPage = hasNextPage;
        this.diseaseList = diseaseList;
    }
}
