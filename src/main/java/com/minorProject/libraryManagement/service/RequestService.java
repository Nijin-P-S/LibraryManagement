package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.models.Request;
import com.minorProject.libraryManagement.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    public Request getRequestById(Integer id){
        return requestRepository.findById(id).orElse(null);
    }

    public Request saveOrUpdateRequest(Request request){
        return requestRepository.save(request);
    }
}
