package com.example.demo.controller;

import com.example.demo.entity.STUDENT;
import com.example.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class MyController extends DefaultController{

    @Autowired
    MyService myService;

    @RequestMapping("/add")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView("addStudent");
        return mv;
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody STUDENT s){
        Map<String, Object> rtnMap = myService.addStudent(s);
        System.out.println("hello world");
        return rtnMap;
    }

    @RequestMapping ("/getStudent")
    public ModelAndView select(@RequestBody Map<String, Object> params) {
        ModelAndView mv = new ModelAndView("hello");
        STUDENT s = myService.selectById(params);
        mv.addObject("student", s);

        return mv;
    }

    @RequestMapping ("/getStudents")
    public ModelAndView selectAll() {
        ModelAndView mv = new ModelAndView("hello");
        List<Map<String, Object>> list = myService.selectAll();

        System.out.println(list.size());
        mv.addObject("students", list);

        return mv;
    }

    @GetMapping("/hello")
    public ModelAndView hello(Model model) {
        logger.info("Hello World");
        ModelAndView mv = new ModelAndView("hello");

        model.addAttribute("name", "Hello World!");
        return mv; // 要導入的html
    }

}

