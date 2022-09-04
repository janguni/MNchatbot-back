package com.petchatbot.repository;

import com.petchatbot.domain.model.Diseases;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DiseasesRepository extends MongoRepository<Diseases, String> {
    //List<Diseases> findByDs_nameRegex(String name)

    @Override
    Optional<Diseases> findById(String s);
}
