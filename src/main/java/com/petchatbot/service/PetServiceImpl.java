package com.petchatbot.service;

import com.petchatbot.domain.dto.PetListDto;
import com.petchatbot.domain.model.*;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.PetReq;
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

    @Transactional
    @Override
    public void registerPet(PetReq petRegReq, String email) {
        Member findMember = getFindMember(email);
        Pet pet = createPetEntity(petRegReq, findMember);
        petRepository.save(pet);
        findMember.addPet(pet);
    }

    @Override
    @Transactional
    public void changePetInfo(ChangePetInfoReq petInfoReq) {
        Pet findPet = getFindPet(petInfoReq);
        log.info("changePet.name = {}",petInfoReq.getPetName());
        log.info("changePet.age = {}",petInfoReq.getPetAge());
        log.info("changePet.gender = {}",petInfoReq.getPetGender());
        log.info("changePet.breed = {}",petInfoReq.getPetBreed());
        log.info("changePet.serial = {}",petInfoReq.getPetSerial());
        log.info("changePet.neutralization = {}",petInfoReq.getPetNeutralization());
        setPet(petInfoReq, findPet);
    }

    @Override
    public List<PetListDto> petList(String email) {
        Member member = memberRepository.findByMemberEmail(email);
        List<Pet> petList = member.getPetList();

        List<PetListDto> pets = new ArrayList<>();
        for (Pet pet: petList){
            //log.info("pet 정보 ={}", pet.getPetName());
            String petName = pet.getPetName();
            int petSerial = pet.getPetSerial();
            Species petSpecies = pet.getPetSpecies();
            PetListDto petListDto = new PetListDto(petSerial, petSpecies, petName);
            pets.add(petListDto);
        }
        return pets;
    }

    @Override
    public PetReq petInfo(int petSerial) {
        Pet pet = petRepository.findByPetSerial(petSerial);
        PetReq petReq = extracted(pet);
        return petReq;
    }

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

    private PetReq extracted(Pet pet) {
        Species petSpecies = pet.getPetSpecies();
        String petBreed = pet.getPetBreed();
        String petName = pet.getPetName();
        int petAge = pet.getPetAge();
        PetGender petGender = pet.getPetGender();
        Neutralization petNeutralization = pet.getPetNeutralization();
        PetReq petReq = new PetReq(petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
        return petReq;
    }

    private void setPet(ChangePetInfoReq petInfoReq, Pet findPet) {
        String petBreed = petInfoReq.getPetBreed();
        String petName = petInfoReq.getPetName();
        int petAge = petInfoReq.getPetAge();
        PetGender petGender = petInfoReq.getPetGender();
        Neutralization petNeutralization = petInfoReq.getPetNeutralization();
        findPet.changePetInfo(petBreed, petName, petAge, petGender, petNeutralization);
    }

    private Pet getFindPet(ChangePetInfoReq petInfoReq) {
        int petSerial = petInfoReq.getPetSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);
        return findPet;
    }

    private Member getFindMember(String email) {
        Member findMember = memberRepository.findByMemberEmail(email);
        return findMember;
    }

    private Pet createPetEntity(PetReq petRegReq, Member findMember) {
        Species petSpecies = petRegReq.getPetSpecies();
        String petBreed = petRegReq.getPetBreed();
        log.info("petSpecies={}", petSpecies);
        String petName = petRegReq.getPetName();
        int petAge = petRegReq.getPetAge();
        PetGender petGender = petRegReq.getPetGender();
        Neutralization petNeutralization = petRegReq.getPetNeutralization();
        Pet pet = new Pet(findMember, petSpecies, petBreed, petName, petAge, petGender, petNeutralization);
        return pet;
    }

}
