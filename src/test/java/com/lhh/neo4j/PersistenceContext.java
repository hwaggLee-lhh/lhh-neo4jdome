package com.lhh.neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("com.lhh.neo4j.repository")
@EnableTransactionManagement
@ComponentScan("com.qinghuainvest.neo4j")
public class PersistenceContext extends Neo4jConfiguration {

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration(){
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
        .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
        .setURI("http://neo4j:a123456@localhost:7474");
        return config;
    }
    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.lhh.neo4j.model");
    }
}
