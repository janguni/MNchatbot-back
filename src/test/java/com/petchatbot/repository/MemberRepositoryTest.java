package com.petchatbot.repository;

import com.petchatbot.domain.dto.EmailDto;
import com.petchatbot.domain.dto.MemberDto;

import com.petchatbot.domain.requestAndResponse.PetReq;
import com.petchatbot.service.MemberService;
import com.petchatbot.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.petchatbot.domain.model.Breed.CAT;
import static com.petchatbot.domain.model.Breed.DOG;
import static com.petchatbot.domain.model.Neutralization.NEUTER;
import static com.petchatbot.domain.model.PetSex.FEMALE;
import static com.petchatbot.domain.model.PetSex.MALE;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    PetService petService;

    @Test
    void 회원탈퇴(){ //지연로딩 발생// No Session
        MemberDto memberDto = new MemberDto("abc@naver.com", "pwpw");
        memberService.join(memberDto);

        PetReq pet1 = new PetReq(DOG, "haru", 2, MALE, NEUTER);
        PetReq pet2 = new PetReq(CAT, "onemac", 3, FEMALE, NEUTER);
        petService.registerPet(pet1, memberDto.getMemberEmail());
        petService.registerPet(pet2, memberDto.getMemberEmail());

        EmailDto emailDto = new EmailDto(memberDto.getMemberEmail());
        petService.petList(emailDto.getReceiveMail());

    }


}