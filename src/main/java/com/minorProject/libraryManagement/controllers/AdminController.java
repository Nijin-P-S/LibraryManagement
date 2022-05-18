package com.minorProject.libraryManagement.controllers;

import com.minorProject.libraryManagement.Requests.CreateAdminRequest;
import com.minorProject.libraryManagement.Requests.ProcessRequest;
import com.minorProject.libraryManagement.models.Admin;
import com.minorProject.libraryManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;


    @PostMapping("/admin")
    public void createAdmin(@Valid @RequestBody CreateAdminRequest adminRequest){
        adminService.createAdmin(adminRequest);
    }

    @GetMapping("/admin/{adminId}")
    public Admin getAdmin(@PathVariable("adminId") int id){
        return adminService.getAdmin(id);
    }

    @PostMapping("/admin/process")
    public void processRequest(@RequestBody ProcessRequest processRequest) throws Exception {
        adminService.processRequest(processRequest);
    }
}
