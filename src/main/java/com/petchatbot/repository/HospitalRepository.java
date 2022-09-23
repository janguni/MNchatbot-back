package com.petchatbot.repository;

import com.petchatbot.domain.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HospitalRepository extends MongoRepository<Hospital, String> {

    public List<Hospital> findByHospRegionAndHospCity(String region, String city);
}
