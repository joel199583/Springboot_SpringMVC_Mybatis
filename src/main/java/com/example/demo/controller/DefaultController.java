package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Object> handleRequestParameter(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = request.getParameter(key).trim() == null ? "" : request.getParameter(key).trim();
            logger.debug(key + " : " + value);
            params.put(key, value);
        }
        //null值轉成空字串
        mapNullAdapter( params, "" );
        return params;
    }

    public static boolean isJSONValid(String jsonInString){
        try{
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void mapNullAdapter(Map<String, Object> params, String assignVal){
        params = params.entrySet().stream().map( entry -> {
            if(entry.getValue() == null)
                entry.setValue(assignVal);
            return entry;
        }).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
        ));
    }

    public static Map<String, Object> handleRequestParameter(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(obj, Map.class);
        return map;
    }
}
