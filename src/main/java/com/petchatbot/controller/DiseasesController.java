package com.petchatbot.controller;

import com.petchatbot.domain.model.Diseases;
import com.petchatbot.repository.DiseasesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiseasesController {

    private final DiseasesRepository diseasesRepository;

    @GetMapping("/diseases/{name}")
    public List<Diseases> searchDiseases(@PathVariable("name") String dsName){
        //List<Diseases> diseasesList = diseasesRepository.findByDs_nameRegex(dsName);
        List<Diseases> all = diseasesRepository.findAll();
        return all;
    }

}
