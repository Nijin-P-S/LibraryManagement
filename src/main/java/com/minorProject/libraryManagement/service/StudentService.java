package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.Requests.PlaceRequest;
import com.minorProject.libraryManagement.Requests.StudentCreateRequest;
import com.minorProject.libraryManagement.models.Admin;
import com.minorProject.libraryManagement.models.Request;
import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.models.User;
import com.minorProject.libraryManagement.repository.RequestRepository;
import com.minorProject.libraryManagement.repository.StudentCacheRepository;
import com.minorProject.libraryManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    //Since the list of autowiring is high, instead of field injection, use constructor injection

    private final String BOOK_INFO_AUTHORITY;
    private final String STUDENT_ONLY_AUTHORITY;
    private final String delimiter;
    private final StudentRepository studentRepository;
    private final RequestService requestService;
    private final AdminService adminService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StudentCacheRepository studentCacheRepository;

    //Constructor Injection
    StudentService(StudentRepository studentRepository, RequestService requestService,
                   AdminService adminService, UserService userService,
                   @Value("${BOOK_INFO_AUTHORITY}") String BOOK_INFO_AUTHORITY,
                   @Value("{STUDENT_ONLY_AUTHORITY}") String STUDENT_ONLY_AUTHORITY,
                   @Value("{$authorities.delimiter}") String delimiter,
                   PasswordEncoder passwordEncoder,
                   StudentCacheRepository studentCacheRepository){
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.requestService =requestService;
        this.adminService = adminService;
        this.delimiter = delimiter;
        this.BOOK_INFO_AUTHORITY =BOOK_INFO_AUTHORITY;
        this.STUDENT_ONLY_AUTHORITY =STUDENT_ONLY_AUTHORITY;
        this.passwordEncoder = passwordEncoder;
        this.studentCacheRepository = studentCacheRepository;
    }


    public void createStudent(StudentCreateRequest studentRequest){
        User userFromRequest = studentRequest.toUser();
        attachAuthorities(userFromRequest);
        encodePassword(userFromRequest);
        User savedUser = userService.saveUser(userFromRequest);

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

    private void attachAuthorities(User user){
        String authorities = STUDENT_ONLY_AUTHORITY+delimiter+BOOK_INFO_AUTHORITY;
        user.setAuthorities(authorities);
    }

    private void encodePassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
