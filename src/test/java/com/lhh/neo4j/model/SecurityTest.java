package com.lhh.neo4j.model;

import com.lhh.neo4j.PersistenceContext;
import com.lhh.neo4j.model.People;
import com.lhh.neo4j.repository.PeopleRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {PersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTest {

    @Autowired
    private Session session;
    @Autowired
    private PeopleRepository securityRepository;
    @Before
    public void purgeDatabase() {
        session.purgeDatabase();
    }
    @Test
    public void save() {
        People security = new People();
        security.setStockCode("430002");
        security.setStockName("中科软");
        securityRepository.save(security);
        System.out.println("OK.");
    }
}
