package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.dto.PetListDto;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.dto.PetDto;
import com.petchatbot.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 1. 반려동물 추가
// 2. 반려동물 프로필 변경
// 3. 반려동물 목록 확인
// 4. 반려동물 세부정보 확인
// 5. 반려동물 삭제

@Slf4j
@RestController
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    /**
     * 반려동물 추가
     * @param petRegReq (반려동물 이름, 나이, 품종, 축종, 성별, 중성화 여부)
     * @param authentication (JWT 토큰 활용)
     * @return 정상: 200/ 그 외 처리x
     */
    @PostMapping("/pet/add")
    public ResponseEntity<String> addPet(@RequestBody PetDto petRegReq, Authentication authentication) {
        String email = extractEmail(authentication);
        petService.registerPet(petRegReq, email);
        log.info("[{}]님의 반려동물 [{}]추가 완료", email ,petRegReq.getPetName());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ADD_PET), HttpStatus.OK);
    }



    /**
     * 반려동물 프로필 변경
     * @param petInfoReq (반려동물 이름, 나이, 축종, 성별, 중성화 여부)
     * @return 정상: 200/ 그 외 처리x
     */
    @PatchMapping("/pet/changeInfo")
    public ResponseEntity<String> changePetInfo(@RequestBody ChangePetInfoReq petInfoReq) {
        petService.changePetInfo(petInfoReq);
        log.info("반려동물 [{}]의 프로필 변경 완료",petInfoReq.getPetName());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_CHANGE_PET_INFO), HttpStatus.OK);
    }


    /**
     * 사용자의 반려동물 목록 확인
     * @param authentication (JWT 활용)
     * @return 정상: 200 / 그 외 처리x
     */
    @GetMapping("/pet/petList")
    public ResponseEntity<List<PetListDto>> petList(Authentication authentication) {
        String email = extractEmail(authentication);
        List<PetListDto> pets = petService.petList(email);
        log.info("[{}]님의 반려동물 목록 확인 완료", email);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_PET_LIST, pets), HttpStatus.OK);
    }


    /**
     * 반려동물 세부정보 확인
     * @param petSerial
     * @return 정상:200 / 반려동물 시리얼로 반려동물을 DB에서 찾지 못한 경우: 404???? => 예외를 터트리는 걸로 변경해야할까 고민
     */
    @GetMapping("/pet/{petSerial}")
    public ResponseEntity<PetDto> petInfo(@PathVariable("petSerial") int petSerial){
        try {
            PetDto petDto = petService.petInfo(petSerial);
            log.info("반려동물 [{}]의 세부정보 확인 완료", petDto.getPetName());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_PET_INFO, petDto), HttpStatus.OK);
        } catch (Exception e){
            log.info("반려동물 정보 없음 = {}", petSerial);
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_GET_PET_INFO, null), HttpStatus.OK);
        }
    }

    /**
     * 반려동물 삭제
     * @param petSerial
     * @return 정상: 200 / 그 외 처리x
     */
    @DeleteMapping("/pet/delete/{petSerial}")
    public ResponseEntity<PetDto> petDelete(@PathVariable("petSerial") int petSerial) {
        petService.petDelete(petSerial);
        log.info("반려동물 (serial:{}) 삭제 완료", petSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE_PET), HttpStatus.OK);
    }


    // authentication 객체로부터 사용자 이메일을 추출
    private String extractEmail(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String memberEmail = principal.getMember().getMemberEmail();
        return memberEmail;
    }
}
