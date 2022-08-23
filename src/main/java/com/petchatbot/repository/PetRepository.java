package com.petchatbot.repository;


import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    Pet findByPetSerial(Long petSerial);
}