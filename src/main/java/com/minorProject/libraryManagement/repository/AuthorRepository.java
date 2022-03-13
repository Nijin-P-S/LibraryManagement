package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

//    : works on ArgumentName , ? works on ArgumentNumber
    @Query("select a from Author a where a.email= :email")
    Author findByEmail(String email);
}
