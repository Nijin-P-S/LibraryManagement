package com.minorProject.libraryManagement.controllers;

import com.minorProject.libraryManagement.Requests.PlaceRequest;
import com.minorProject.libraryManagement.Requests.StudentCreateRequest;
import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    //Not authenticated
    @PostMapping("/student")
    public void createStudent(@Valid @RequestBody StudentCreateRequest studentRequest){
        studentService.createStudent(studentRequest);
    }

    //Admin
    @GetMapping("/studentById/{studentId}")
    public Student getStudent(@PathVariable("studentId") int id){
        return studentService.getStudentById(id);
    }

    //Student
    @GetMapping("/student")
    public Student getStudent(){
        return null;
    }

    //Student
    @PostMapping("/student/request")
    public String placeRequest(@Valid @RequestBody PlaceRequest placeRequest){
        return studentService.placeRequest(placeRequest);
    }

}
