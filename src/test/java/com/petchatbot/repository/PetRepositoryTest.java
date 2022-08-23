package com.petchatbot.repository;

import com.petchatbot.domain.model.Pet;
import com.petchatbot.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.petchatbot.domain.model.Breed.DOG;
import static com.petchatbot.domain.model.Neutralization.NEUTER;
import static com.petchatbot.domain.model.PetSex.MALE;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Test
    void changPetInfo(){
        Pet pet = new Pet(DOG, "haru",2,MALE, NEUTER);
        Pet petpet = petRepository.save(pet);
        assertThat(pet.getPetName()).isEqualTo(petpet.getPetName());

        Pet findPet = petRepository.findByPetSerial(pet.getPetSerial());
        assertThat(pet).isEqualTo(findPet);

    }

}