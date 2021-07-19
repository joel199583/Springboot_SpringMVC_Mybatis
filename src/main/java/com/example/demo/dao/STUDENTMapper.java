package com.example.demo.dao;

import com.example.demo.entity.STUDENT;

import java.util.List;
import java.util.Map;

public interface STUDENTMapper {
    int insertStudentInfo(STUDENT s);

    STUDENT selectStudentById(Map<String, Object> params);

    List<Map<String, Object>> selectAll();
}