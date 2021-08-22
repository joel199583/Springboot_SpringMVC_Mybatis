package com.example.demo.service;

import com.example.demo.dao.db1.STUDENTMapper;
import com.example.demo.dao.db2.TEACHERMapper;
import com.example.demo.entity.STUDENT;
import com.example.demo.entity.TEACHER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSuspensionNotSupportedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyService {
    @Autowired(required = false)
    STUDENTMapper studentMapper;

    @Autowired(required = false)
    TEACHERMapper teacherMapper;

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

    @Transactional(value = "jtaTransactionManager", rollbackFor = Exception.class)
    public void insertTwoObj() throws Exception {
        try {
            STUDENT student = new STUDENT();
            student.setName("Joel Xu");
            int i = studentMapper.insertStudentInfo(student);
            if(i>0){
                System.out.println("學生新增成功");
            } else {
                System.out.println("學生新增失敗");
            }

            //故意造成例外測試回滾
//            System.out.println(Integer.valueOf("sss"));

            TEACHER teacher = new TEACHER();
            teacher.setName("Peter Wu");
            int j = teacherMapper.insertTeacherInfo(teacher);
            if(j>0){
                System.out.println("老師新增成功");
            } else {
                System.out.println("老師新增失敗");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("回滾成功");
            e.printStackTrace();
        }

    }
}
