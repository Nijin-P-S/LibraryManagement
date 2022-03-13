package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.Requests.CreateAdminRequest;
import com.minorProject.libraryManagement.Requests.ProcessRequest;
import com.minorProject.libraryManagement.Responses.ProcessResponse;
import com.minorProject.libraryManagement.models.Admin;
import com.minorProject.libraryManagement.models.Request;
import com.minorProject.libraryManagement.models.RequestStatus;
import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private static final String REJECTED_STATUS = "REJECTED";
    private static final String APPROVED_STATUS = "APPROVED";

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RequestService requestService;

    @Autowired
    TransactionService transactionService;

    public void createAdmin(CreateAdminRequest adminRequest) {
        adminRepository.save(adminRequest.to());
    }

    public Admin getAdmin(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    public ProcessResponse processRequest(ProcessRequest processRequest) throws Exception {
        Request request = requestService.getRequestById(processRequest.getRequestId());
        if(request==null) {
            throw new Exception("Request doesn't exist");
        }

        if(request.getAdmin()==null || request.getAdmin().getId()!= processRequest.getAdminId()){
            throw new Exception("This request is not assigned to this Admin "+processRequest.getAdminId());
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
}
