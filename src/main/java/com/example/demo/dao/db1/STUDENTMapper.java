package com.example.demo.dao.db1;

import com.example.demo.entity.STUDENT;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;
import java.util.Map;


public interface STUDENTMapper {
    int insertStudentInfo(STUDENT s);

    STUDENT selectStudentById(Map<String, Object> params);

    List<Map<String, Object>> selectAll();
}