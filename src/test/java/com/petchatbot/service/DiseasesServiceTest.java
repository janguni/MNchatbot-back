package com.petchatbot.service;

import com.petchatbot.domain.model.Diseases;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class DiseasesServiceTest {

    @Autowired
    DiseasesService diseasesService;

    @Test
    void diseaseList(){
        List<Diseases> allDiseases = diseasesService.findAllDiseases();
        log.info("diseases={}", allDiseases);
    }

}