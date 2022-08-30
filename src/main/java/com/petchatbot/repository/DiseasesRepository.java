package com.petchatbot.repository;

import com.petchatbot.domain.model.Diseases;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseasesRepository extends MongoRepository<Diseases, ObjectId> {

    @Query("{ds_name:/name/}")
    List<Diseases> findItemByName(String name);

    @Override
    List<Diseases> findAll();
}
