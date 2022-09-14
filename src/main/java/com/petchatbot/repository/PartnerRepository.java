package com.petchatbot.repository;

import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {

    List<Partner> findByPnrRegionAndPnrCityAndPnrStreet(String region, String city, String street);
}
