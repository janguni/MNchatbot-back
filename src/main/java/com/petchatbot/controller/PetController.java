package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.JoinReq;
import com.petchatbot.domain.requestAndResponse.PetRegReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.service.MemberService;
import com.petchatbot.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PetController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PetService petService;

    // 반려동물 추가
    @PostMapping("/pet/add")
    public ResponseEntity<String> addPet(@RequestBody PetRegReq petRegReq, Authentication authentication) {
        String email = extractEmail(authentication);
        petService.registerPet(petRegReq, email);
        log.info("addPet = {}",petRegReq.getPetName());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ADD_PET), HttpStatus.OK);
    }

    // 반려동물 프로필 변경
    @PatchMapping("/pet/changeInfo")
    public ResponseEntity<String> changePetInfo(@RequestBody ChangePetInfoReq petInfoReq) {
        petService.changePetInfo(petInfoReq);
        log.info("changePetInfo = {}",petInfoReq.getPetName());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_CHANGE_PET_INFO), HttpStatus.OK);
    }

    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}
