package com.petchatbot.repository;

import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalFormRepository extends JpaRepository<MedicalForm, Integer> {

    MedicalForm findByMedicalFormSerial(int medicalSerial);
    List<MedicalForm> findByPet(Pet pet);
}
