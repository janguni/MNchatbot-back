package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.PetListDto;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.PetReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.service.MemberService;
import com.petchatbot.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PetController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PetService petService;

    // 반려동물 추가
    @PostMapping("/pet/add")
    public ResponseEntity<String> addPet(@RequestBody PetReq petRegReq, Authentication authentication) {
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

     // 반려동물 List
    @GetMapping("/pet/petList")
    public ResponseEntity<List<PetListDto>> petList(Authentication authentication) {
        String email = extractEmail(authentication);
        log.info("펫 리스트 요청이 들어옴!!!");
        log.info("petlist를 찾는 사용자={}", email);
        List<PetListDto> pets = petService.petList(email);
        log.info("pets={}", pets);
        if (pets.isEmpty()){
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_PET_LIST), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_PET_LIST, pets), HttpStatus.OK);
    }

//    @GetMapping("/pet/petList")
//    public String petList(Authentication authentication) {
//        String email = extractEmail(authentication);
//        log.info("펫 리스트 요청이 들어옴!!!");
//        log.info("petlist를 찾는 사용자={}", email);
//        return "토큰도 됨!";
//    }




    // 반려동물 정보
    @GetMapping("/pet/{petSerial}")
    public ResponseEntity<PetReq> petInfo(@PathVariable("petSerial") Long petSerial){
        try {
            PetReq petReq = petService.petInfo(petSerial);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_PET_INFO, petReq), HttpStatus.OK);
        } catch (Exception e){
            log.info("반려동물 정보 없음 = {}", petSerial);
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_PET_INFO, null), HttpStatus.OK);
        }
    }

    // 반려동물 삭제
    @DeleteMapping("/pet/delete/{petSerial}")
    public ResponseEntity<PetReq> petDelete(@PathVariable("petSerial") Long petSerial) {
        petService.petDelete(petSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE_PET), HttpStatus.OK);
    }

    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}
