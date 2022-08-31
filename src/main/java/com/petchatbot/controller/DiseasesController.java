package com.petchatbot.controller;

import com.petchatbot.domain.model.Diseases;
import com.petchatbot.repository.DiseasesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiseasesController {

    private DiseasesRepository diseasesRepository;

    @GetMapping("/diseases/{id}")
    public List<Diseases> searchDiseases(@PathVariable("id") ObjectId id){
        //List<Diseases> diseasesList = diseasesRepository.findById(id);
        return null;
    }

}
