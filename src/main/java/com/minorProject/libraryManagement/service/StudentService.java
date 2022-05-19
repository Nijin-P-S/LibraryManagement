package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.Requests.PlaceRequest;
import com.minorProject.libraryManagement.Requests.StudentCreateRequest;
import com.minorProject.libraryManagement.models.Admin;
import com.minorProject.libraryManagement.models.Request;
import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.models.User;
import com.minorProject.libraryManagement.repository.RequestRepository;
import com.minorProject.libraryManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RequestService requestService;

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    public void createStudent(StudentCreateRequest studentRequest){

        User savedUser = userService.saveUser(studentRequest.toUser());

        studentRepository.save(studentRequest.to(savedUser));
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public String placeRequest(PlaceRequest placeRequest, Integer studentId){
        List<Admin> admins = adminService.getAllAdmin();

        Admin admin = admins.stream()
                .min((a1, a2) -> a1.getRequestsToProcess().size() - a2.getRequestsToProcess().size())
                .orElse(null);

        return requestService.saveOrUpdateRequest(placeRequest.toRequest(admin, studentId)).getRequestId();
    }


}
