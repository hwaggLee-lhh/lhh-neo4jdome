package com.lhh.neo4j.model;

import com.lhh.neo4j.core.AbstractEntity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class People extends AbstractEntity {

    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private String stockCode;
    private String stockName;
    @Relationship(type="BROKER")
    private School broker;
    @Relationship(type="MKTMAKER")
    private Set<School> mktMarker = new HashSet<School>();
    @Relationship(type="SUPERVISER")
    private School superviser;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public School getSuperviser() {
        return superviser;
    }

    public void setSuperviser(School superviser) {
        this.superviser = superviser;
    }

    public School getBroker() {
        return broker;
    }

    public void setBroker(School broker) {
        this.broker = broker;
    }

    public Set<School> getMktMarker() {
        return mktMarker;
    }

    public void setMktMarker(Set<School> mktMarker) {
        this.mktMarker = mktMarker;
    }
}
