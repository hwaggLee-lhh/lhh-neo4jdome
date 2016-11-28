package com.lhh.neo4j.repository;

import com.lhh.neo4j.model.People;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface PeopleRepository extends GraphRepository<People> {
    People findById(Long id);
}
