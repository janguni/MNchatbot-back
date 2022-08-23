package com.petchatbot.service;

import com.petchatbot.domain.model.*;
import com.petchatbot.domain.requestAndResponse.ChangePetInfoReq;
import com.petchatbot.domain.requestAndResponse.PetRegReq;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService{

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;

    @Transactional
    @Override
    public void registerPet(PetRegReq petRegReq) {
        Member findMember = getFindMember(petRegReq.getMemberEmail());
        Pet pet = createPetEntity(petRegReq);
        petRepository.save(pet);
        findMember.addPet(pet);
    }

    @Override
    @Transactional
    public void changePetInfo(ChangePetInfoReq petInfoReq) {
        Pet findPet = getFindPet(petInfoReq);
        setPet(petInfoReq, findPet);
    }

    private void setPet(ChangePetInfoReq petInfoReq, Pet findPet) {
        Breed petBreed = petInfoReq.getPetBreed();
        String petName = petInfoReq.getPetName();
        int petAge = petInfoReq.getPetAge();
        PetSex petSex = petInfoReq.getPetSex();
        Neutralization petNeutralization = petInfoReq.getPetNeutralization();
        findPet.changePetInfo(petBreed, petName, petAge, petSex, petNeutralization);
    }

    private Pet getFindPet(ChangePetInfoReq petInfoReq) {
        Long petSerial = petInfoReq.getPetSerial();
        log.info("petSerial={}", petSerial);
        Pet findPet = petRepository.findByPetSerial(petSerial);
        return findPet;
    }

    private Member getFindMember(String email) {
        Member findMember = memberRepository.findByMemberEmail(email);
        return findMember;
    }

    private Pet createPetEntity(PetRegReq petRegReq) {
        Breed petBreed = petRegReq.getPetBreed();
        log.info("petBreed={}", petBreed);
        String petName = petRegReq.getPetName();
        int petAge = petRegReq.getPetAge();
        PetSex petSex = petRegReq.getPetSex();
        Neutralization petNeutralization = petRegReq.getPetNeutralization();

        Pet pet = new Pet(petBreed, petName, petAge, petSex, petNeutralization);
        return pet;
    }


}
