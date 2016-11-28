package com.lhh.neo4j.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhh.neo4j.PersistenceContext;
import com.lhh.neo4j.model.School;
import com.lhh.neo4j.model.People;
import com.lhh.neo4j.repository.SchoolRepository;
import com.lhh.neo4j.repository.PeopleRepository;
import com.lhh.neo4j.utils.HttpUtil;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@ContextConfiguration(classes = {PersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InitDb {
    @Autowired
    private Session session;
    @Autowired
    private PeopleRepository securityRepository;
    @Autowired
    private SchoolRepository brokerRepository;

    private Map<String,String> superviseMap = new HashMap<String, String>();
    private Map<String,String> brokerRelaMap = new HashMap<String, String>();
    private Map<String,List<String>> mktMakerRelaMap = new HashMap<String, List<String>>();

    private Map<String, School> brokerMap = new HashMap<String, School>();
    @Before
    public void purgeDatabase() {
        session.purgeDatabase();
    }
    @Test
    public void init() {
        initBroker();
        initSecurity();
        System.out.println("OK.");
    }
    private void initBroker() {
        try {
            InputStream is = InitDb.class.getResourceAsStream("/broker.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(read);
            String lineStr;
            while(StringUtils.isNotBlank(lineStr = br.readLine())) {
                String[] s = lineStr.split("\t");
                if(s==null || s.length!=2) continue;
                School broker = brokerRepository.save(new School(s[0],s[1]));
                brokerMap.put(broker.getCode(), broker);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void initSecurity() {
        String uri = "http://61.152.154.23:8081/web/si/baseinfo_list";
        JSONObject jsonObject = HttpUtil.getRemoteData(uri);
        if(jsonObject==null) return;
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        if(jsonArray==null || jsonArray.size()==0) return;
        for (Object obj:jsonArray ) {
            if(obj instanceof JSONObject){} else continue;
            JSONObject json = (JSONObject)obj;
            saveSecurity(json);
        }
    }
    private void saveSecurity(JSONObject json) {
        People security = new People();
        String securityCode = json.getString("xxzqdm");
        security.setStockCode(securityCode);
        security.setStockName(json.getString("xxzqjc"));
        security.setSuperviser(getSuperviseBroker(securityCode));
        security.setBroker(getBroker(securityCode));
        security.setMktMarker(getMktMakerBroker(securityCode));
        System.out.println(security.getMktMarker());
        securityRepository.save(security);
    }
    private School getSuperviseBroker(String securityCode) {
        if(superviseMap==null || superviseMap.isEmpty()) {
            initRelation("/supervise_rela.txt", superviseMap);
        }
        String brokerCode = superviseMap.get(securityCode);
        if(StringUtils.isBlank(brokerCode)) return null;
        return brokerMap.get(brokerCode);
    }
    private School getBroker(String securityCode) {
        if(brokerRelaMap==null || brokerRelaMap.isEmpty()) {
            initRelation("/broker_rela.txt", brokerRelaMap);
        }
        String brokerCode = brokerRelaMap.get(securityCode);
        if(StringUtils.isBlank(brokerCode)) return null;
        return brokerMap.get(brokerCode);
    }
    private Set<School> getMktMakerBroker(String securityCode) {
        if(mktMakerRelaMap==null || mktMakerRelaMap.isEmpty()) {
            String fileName = "/mktmaker_rela.txt";
            try {
                InputStream is = InitDb.class.getResourceAsStream(fileName);
                InputStreamReader read = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(read);
                String lineStr;
                while(StringUtils.isNotBlank(lineStr = br.readLine())) {
                    String[] s = lineStr.split("\t");
                    if(s==null || s.length!=2) continue;
                    List<String> codes = mktMakerRelaMap.get(s[0]);
                    if(codes == null) {
                        codes = new ArrayList<String>();
                        mktMakerRelaMap.put(s[0],codes);
                    }
                    codes.add(s[1]);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        List<String> brokerCodes = mktMakerRelaMap.get(securityCode);
        if(brokerCodes==null || brokerCodes.size()==0) return null;
        Set<School> brokerSet = new HashSet<School>();
        for (String code:brokerCodes ) {
            School broker = brokerMap.get(code);
            if(broker == null) continue;
            brokerSet.add(broker);
        }
        return brokerSet;
    }
    private static void initRelation(String fileName, Map<String, String> map) {
        try {
            InputStream is = InitDb.class.getResourceAsStream(fileName);
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(read);
            String lineStr;
            while(StringUtils.isNotBlank(lineStr = br.readLine())) {
                String[] s = lineStr.split("\t");
                if(s==null || s.length!=2) continue;
                map.put(s[0],s[1]);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
