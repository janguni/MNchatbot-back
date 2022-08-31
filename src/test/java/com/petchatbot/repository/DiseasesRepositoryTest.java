package com.petchatbot.repository;

import com.petchatbot.domain.model.Diseases;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class DiseasesRepositoryTest {

    @Autowired
    DiseasesRepository diseasesRepository;

    @Test
    void 질병명_찾기(){
        //List<Diseases> diseasesList = diseasesRepository.findByDs_nameRegex("갑상선");
        //log.info("질병list={}",diseasesList);
    }

}