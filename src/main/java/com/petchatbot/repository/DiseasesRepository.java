package com.petchatbot.repository;

import com.petchatbot.domain.model.Diseases;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiseasesRepository extends MongoRepository<Diseases, String> {

    @Query("{ds_name:/name/}")
    List<Diseases> findItemByName(String name);

}
