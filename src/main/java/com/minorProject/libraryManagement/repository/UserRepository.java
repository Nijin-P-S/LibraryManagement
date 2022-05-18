package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
