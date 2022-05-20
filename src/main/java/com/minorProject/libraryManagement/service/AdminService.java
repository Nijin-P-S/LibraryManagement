package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.Requests.CreateAdminRequest;
import com.minorProject.libraryManagement.Requests.ProcessRequest;
import com.minorProject.libraryManagement.Responses.ProcessResponse;
import com.minorProject.libraryManagement.models.*;
import com.minorProject.libraryManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private static final String REJECTED_STATUS = "REJECTED";
    private static final String APPROVED_STATUS = "APPROVED";


    private final String ADMIN_AUTHORITY;
    private final String BOOK_INFO_AUTHORITY;
    private final String STUDENT_INFO_AUTHORITY;
    private final String delimiter;
    private final AdminRepository adminRepository;
    private final RequestService requestService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    AdminService(UserService userService, TransactionService transactionService,
                              RequestService requestService, AdminRepository adminRepository,
                              @Value("{$authorities.delimiter}") String delimiter,
                              @Value("${STUDENT_INFO_AUTHORITY}") String STUDENT_INFO_AUTHORITY,
                              @Value("${BOOK_INFO_AUTHORITY}") String BOOK_INFO_AUTHORITY,
                              @Value("${ADMIN_AUTHORITY}") String ADMIN_AUTHORITY,
                              PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.transactionService = transactionService;
        this.requestService = requestService;
        this.adminRepository = adminRepository;
        this.delimiter = delimiter;
        this.STUDENT_INFO_AUTHORITY = STUDENT_INFO_AUTHORITY;
        this.BOOK_INFO_AUTHORITY = BOOK_INFO_AUTHORITY;
        this.ADMIN_AUTHORITY = ADMIN_AUTHORITY;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAdmin(CreateAdminRequest createAdminRequest) {
        User userFromRequest = createAdminRequest.toUser();
        attachAuthorities(userFromRequest);
        encodePassword(userFromRequest);
        User savedUser = userService.saveUser(userFromRequest);

        adminRepository.save(createAdminRequest.to(savedUser));
    }

    public Admin getAdminById(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    public ProcessResponse processRequest(ProcessRequest processRequest, Integer adminId) throws Exception {
        Request request = requestService.getRequestById(processRequest.getRequestId());
        if(request==null) {
            throw new Exception("Request doesn't exist");
        }

        if(request.getAdmin()==null || request.getAdmin().getId()!= adminId){
            throw new Exception("This request is not assigned to this Admin "+adminId);
        }

        if(!request.getRequestStatus().equals(RequestStatus.PENDING)){
            throw new Exception("This request is already processed ");
        }

        if(RequestStatus.REJECTED.equals(processRequest.getRequestStatus())){
            request.setRequestStatus(RequestStatus.REJECTED);
            request.setAdminComment(processRequest.getComment());
            request.setSystemComment(REJECTED_STATUS);
            requestService.saveOrUpdateRequest(request);//If the status is Rejected, then just update it from Pending to Rejected without creating new entry
            return ProcessResponse.builder()
                    .systemComment(REJECTED_STATUS)
                    .requestStatus(RequestStatus.REJECTED)
                    .adminComment(processRequest.getComment())
                    .build();
        }


        request.setRequestStatus(RequestStatus.ACCEPTED);
        request.setAdminComment(processRequest.getComment());
        request.setSystemComment(APPROVED_STATUS);
        Request savedRequest = requestService.saveOrUpdateRequest(request);
        transactionService.createTransaction(savedRequest);
        return ProcessResponse.builder()
                .systemComment(APPROVED_STATUS)
                .requestStatus(RequestStatus.ACCEPTED)
                .adminComment(processRequest.getComment())
                .build();
    }

    private void attachAuthorities(User user){
        String authorities = BOOK_INFO_AUTHORITY + delimiter + ADMIN_AUTHORITY + delimiter + STUDENT_INFO_AUTHORITY;
        user.setAuthorities(authorities);
    }

    private void encodePassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
