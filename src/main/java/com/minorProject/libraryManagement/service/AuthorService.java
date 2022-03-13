package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.models.Author;
import com.minorProject.libraryManagement.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author createOrGetAuthor(Author author){
        Author retrievedAuthor = getAuthorByEmail(author.getEmail());

        if(retrievedAuthor != null)
            return retrievedAuthor;

        return authorRepository.save(author);
    }

    public Author getAuthorByEmail(String email){
        return authorRepository.findByEmail(email);
    }
}
