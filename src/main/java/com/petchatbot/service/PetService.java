package com.petchatbot.service;

import com.petchatbot.domain.dto.PetListDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.dto.PetDto;

import java.util.List;

public interface PetService {

    // 반려동물 추가
    void registerPet(PetDto petRegReq, String email);

    // 반려동물 변경
    void changePetInfo(ChangePetInfoReq petInfoReq);

    // 반려동물 list
    List<PetListDto> petList(Member member);

    // 반려동물 info
    PetDto petInfo(int petSerial);

    // 반려동물 삭제
    void petDelete(int petSerial);

}
