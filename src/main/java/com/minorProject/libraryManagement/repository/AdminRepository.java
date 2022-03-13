package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
