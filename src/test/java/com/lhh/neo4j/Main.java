package com.lhh.neo4j;

import com.lhh.neo4j.model.People;
import com.lhh.neo4j.repository.PeopleRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class Main {
    @Autowired
    PeopleRepository repo;
    public static void main(String... args) {
        System.out.println("Hello world.");
        Main main = new Main();
        People security = new People();
        security.setStockCode("430002");
        security.setStockCode("中科软");

        main.repo.save(security);
    }
}
