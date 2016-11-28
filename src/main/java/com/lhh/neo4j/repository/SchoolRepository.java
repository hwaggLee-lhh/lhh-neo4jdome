package com.lhh.neo4j.repository;

import com.lhh.neo4j.model.School;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface SchoolRepository extends GraphRepository<School> {
    School findById(Long id);

    School findByCode(String code);
}
