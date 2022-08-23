package com.petchatbot.service;

import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.PetRegReq;

public interface PetService {

    // 반려동물 추가
    void registerPet(PetRegReq petRegReq);

    // 반려동물 변경
    void changePetInfo(ChangePetInfoReq petInfoReq);

}
