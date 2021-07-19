package com.example.demo.service;

import com.example.demo.dao.STUDENTMapper;
import com.example.demo.entity.STUDENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyService {
    @Autowired(required = false)
    STUDENTMapper studentMapper;

    public Map<String, Object> addStudent(STUDENT student){

        Map<String, Object> rtnMap = new HashMap<>();
        int i = studentMapper.insertStudentInfo(student);

        if(i>0){
            rtnMap.put("flag", "ok");
            rtnMap.put("msg", "新增成功");
        } else {
            rtnMap.put("flag", "ng");
            rtnMap.put("msg", "新增失敗");
        }

        return rtnMap;
    }

    public STUDENT selectById(Map<String, Object> params){
        return studentMapper.selectStudentById(params);
    }

    public List<Map<String, Object>> selectAll(){
        return studentMapper.selectAll();
    }
}
