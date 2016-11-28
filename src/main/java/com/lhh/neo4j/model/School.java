package com.lhh.neo4j.model;

import com.lhh.neo4j.core.AbstractEntity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class School extends AbstractEntity {
    public School() {

    }
    public School(String code, String name){
        this.code = code;
        this.name = name;
    }
    @GraphId
    private Long id;
    private String code;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
