package com.petchatbot.repository;

import com.petchatbot.domain.dto.EmailDto;
import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.model.Member;

import com.petchatbot.domain.requestAndResponse.PetRegReq;
import com.petchatbot.service.MemberService;
import com.petchatbot.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.petchatbot.domain.model.Breed.DOG;
import static com.petchatbot.domain.model.Neutralization.NEUTER;
import static com.petchatbot.domain.model.PetSex.MALE;
import static org.junit.jupiter.api.Assertions.*;

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
    void 회원탈퇴(){
        MemberDto memberDto = new MemberDto("abc@naver.com", "pwpw");
        memberService.join(memberDto);

        PetRegReq petRegReq = new PetRegReq(DOG, "haru", 2, MALE, NEUTER);
        petService.registerPet(petRegReq, memberDto.getMemberEmail());

        EmailDto emailDto = new EmailDto(memberDto.getMemberEmail());
        memberService.withdrawal(emailDto);

    }


}