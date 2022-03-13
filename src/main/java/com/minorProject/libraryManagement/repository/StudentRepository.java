package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
