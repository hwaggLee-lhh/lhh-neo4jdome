package com.lhh.neo4j.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
public class HttpUtil {
    public static String execute(String url) {
        HttpClient httpClient = new HttpClient();
        PostMethod getMethod = new PostMethod(url);
        getMethod.addRequestHeader("Connection", "close");
        try {
            httpClient.executeMethod(getMethod);
            byte[] responseBody = getMethod.getResponseBody();
            String responseStr = new String(responseBody,"utf-8");
            return responseStr;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject getRemoteData(String url) {
        String result = HttpUtil.execute(url);
        if(StringUtils.isNotBlank(result)) {
            try {
                return JSONObject.parseObject(result);
            } catch (Exception e) {
            }
        }
        return new JSONObject();
    }
}
