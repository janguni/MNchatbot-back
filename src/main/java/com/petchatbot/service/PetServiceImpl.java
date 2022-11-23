package com.petchatbot.service;

import com.petchatbot.domain.dto.PetListDto;
import com.petchatbot.domain.model.*;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.dto.PetDto;
import com.petchatbot.repository.ExpectDiagnosisRepository;
import com.petchatbot.repository.MedicalFormRepository;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService{

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final ExpectDiagnosisService expectDiagnosisService;
    private final ExpectDiagnosisRepository expectDiagnosisRepository;
    private final MedicalFormRepository medicalFormRepository;
    private final MedicalFormService medicalFormService;

    /**
     * 반려동물 추가
     * @param petDto (반려동물 등록 시 필요한 정보)
     * @param email (사용자 이메일)
     */
    @Transactional
    @Override
    public void registerPet(PetDto petDto, String email) {
        Member findMember = getFindMember(email);
        Pet pet = getPetFromPetDto(petDto, findMember);
        petRepository.save(pet); // 반려동물 db에 저장
        findMember.addPet(pet); // 사용자의 petList에 pet 저장 (단방향 매핑)???
    }

    /**
     * 반려동물 프로필 변경
     * @param petInfoReq (품종을 제외한 반려동물 정보)
     */
    @Override
    @Transactional
    public void changePetInfo(ChangePetInfoReq petInfoReq) {
        Pet findPet = getFindPet(petInfoReq);
        setPetInfo(petInfoReq, findPet);
    }

    /**
     * 반려동물 목록 확인
     * @param findMember
     * @return 반려동물들의 시리얼, 품종, 이름 정보만
     */
    @Override
    public List<PetListDto> petList(Member findMember) {
        List<Pet> petList = findMember.getPetList();

        List<PetListDto> pets = new ArrayList<>();
        for (Pet pet: petList){
            String petName = pet.getPetName();
            int petSerial = pet.getPetSerial();
            Species petSpecies = pet.getPetSpecies();
            PetListDto petListDto = new PetListDto(petSerial, petSpecies, petName);
            pets.add(petListDto);
        }
        return pets;
    }

    /**
     * 반려동물의 세부정보
     * @param petSerial
     * @return 반려동물 세부정보
     */
    @Override
    public PetDto petInfo(int petSerial) {
        Pet pet = petRepository.findByPetSerial(petSerial);
        PetDto petDto = getPetDtoFromPet(pet);
        return petDto;
    }

    /**
     * 반려동물 삭제
     * @param petSerial
     */
    @Override
    @Transactional
    public void petDelete(int petSerial) {
        // 해당 반려동물의 예상질병 삭제
        Pet findPet = petRepository.findByPetSerial(petSerial);
        List<ExpectDiagnosis> expectDiagnoses = expectDiagnosisRepository.findByPet(findPet);
        for (ExpectDiagnosis e: expectDiagnoses){
            expectDiagnosisService.deleteExpectDiag(e.getDiagSerial());
        }

        // 해당 반려동물 의 문진표 삭제
        List<MedicalForm> medicalForms = medicalFormRepository.findByPet(findPet);
        for (MedicalForm m: medicalForms) {
            medicalFormService.deleteMedicalForm(m.getMedicalFormSerial());
        }

        // 반려동물 삭제
        petRepository.delete(findPet);
    }

    // Pet 객체로부터 PetDto 객체 생성해서 반환
    private PetDto getPetDtoFromPet(Pet pet) {
        Species petSpecies = pet.getPetSpecies();
        String petBreed = pet.getPetBreed();
        String petName = pet.getPetName();
        int petAge = pet.getPetAge();
        PetGender petGender = pet.getPetGender();
        Neutralization petNeutralization = pet.getPetNeutralization();
        PetDto petDto = new PetDto(petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
        return petDto;
    }

    // petDto 객체로부터 Pet 객체 생성해서 반환 (바로 위의 함수와 반대)
    private Pet getPetFromPetDto(PetDto petDto, Member findMember) {
        Species petSpecies = petDto.getPetSpecies();
        String petBreed = petDto.getPetBreed();
        String petName = petDto.getPetName();
        int petAge = petDto.getPetAge();
        PetGender petGender = petDto.getPetGender();
        Neutralization petNeutralization = petDto.getPetNeutralization();
        Pet pet = new Pet(findMember, petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
        return pet;
    }

    // petInfoReq객체로 Pet객체 생성해서 반환
    private Pet getFindPet(ChangePetInfoReq petInfoReq) {
        int petSerial = petInfoReq.getPetSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);
        return findPet;
    }

    // 수정할 반려동물 세부정보와 Pet 객체로 반려동물 데이터 변경
    private void setPetInfo(ChangePetInfoReq petInfoReq, Pet findPet) {
        String petBreed = petInfoReq.getPetBreed();
        String petName = petInfoReq.getPetName();
        int petAge = petInfoReq.getPetAge();
        PetGender petGender = petInfoReq.getPetGender();
        Neutralization petNeutralization = petInfoReq.getPetNeutralization();
        findPet.changePetInfo(petBreed, petName, petAge, petGender, petNeutralization);
    }

    // email 로부터 Member 객체 찾아서 반환
    private Member getFindMember(String email) {
        Member findMember = memberRepository.findByMemberEmail(email);
        return findMember;
    }
}
