package com.minorProject.libraryManagement.controllers;

import com.minorProject.libraryManagement.Requests.BookCreateRequest;
import com.minorProject.libraryManagement.Requests.BookFilterQuery;
import com.minorProject.libraryManagement.Requests.StudentCreateRequest;
import com.minorProject.libraryManagement.Responses.BookResponse;
import com.minorProject.libraryManagement.models.Book;
import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.service.BookService;
import com.minorProject.libraryManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    //Admin
    @PostMapping("/book")
    public Book createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        return bookService.createBook(bookCreateRequest);
    }

    //Student + Admin
    //Filteration process
    @GetMapping("/book")
    public List<BookResponse> getBook(@RequestParam("filterType") String filterType,
                                      @RequestParam("filterValue") String filterValue){
        switch (BookFilterQuery.valueOf(filterType)){
            case ID:
                return bookService.getBookById(Integer.parseInt(filterValue));
            case GENRE:
                return bookService.getBookByGenre(filterValue);
            case AUTHOR:
                return bookService.getBookByAuthorEmail(filterValue);
            case AVAILABILITY:
                return bookService.getBookByAvail(Boolean.valueOf(filterValue));
        }
        return null;
    }

    // 1. Get all the books which are written by X author
    // 2. Get all books of genre X
    // 3. Get all available books
    // 4. Get bppk given a book Id

}
