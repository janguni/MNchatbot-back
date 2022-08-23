package com.petchatbot.service;

//import com.petchatbot.domain.dto.MemberDto;
//import com.petchatbot.domain.model.*;
//import com.petchatbot.domain.requestAndResponse.PetRegReq;
//import com.petchatbot.repository.MemberRepository;
//import com.petchatbot.repository.PetRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Slf4j
//class AnimalServiceImplTest {
//
//    @Autowired
//    PetService petServiceImpl;
//
//    @Autowired
//    MemberService memberService;
//
//
//    @Test
//    void 반려동물추가(){
//        MemberDto memberDto = new MemberDto("abc@naver.com", "password");
//        memberService.join(memberDto);
//        PetRegReq petRegReq = new PetRegReq("abc@naver.com", Breed.DOG, "hera", 33, PetSex.FEMALE, Neutralization.NOTNEUTER);
//
//        petServiceImpl.registerPet(petRegReq);
//
//    }
//
//
//
//
//}