package com.petchatbot.repository;

import com.petchatbot.domain.model.ExpectDiagnosis;
import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpectDiagnosisRepository extends JpaRepository<ExpectDiagnosis, Integer> {

    ExpectDiagnosis findByDiagSerial(int diagSerial);
    List<ExpectDiagnosis> findByPet(Pet pet);
}
