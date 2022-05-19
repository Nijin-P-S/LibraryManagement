package com.minorProject.libraryManagement.controllers;

import com.minorProject.libraryManagement.Requests.PlaceRequest;
import com.minorProject.libraryManagement.Requests.StudentCreateRequest;
import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.models.User;
import com.minorProject.libraryManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        /*
            1.Get the student id from authentication context
            2.Get student by student_id
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int studentId = user.getStudent().getId();
        return studentService.getStudentById(studentId);
    }

    //Student
    @PostMapping("/student/request")
    public String placeRequest(@Valid @RequestBody PlaceRequest placeRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int studentId = user.getStudent().getId();

        return studentService.placeRequest(placeRequest, studentId);
    }

}
