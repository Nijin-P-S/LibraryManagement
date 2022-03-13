package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.Requests.BookCreateRequest;
import com.minorProject.libraryManagement.Responses.BookResponse;
import com.minorProject.libraryManagement.models.Author;
import com.minorProject.libraryManagement.models.Book;
import com.minorProject.libraryManagement.models.Genre;
import com.minorProject.libraryManagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;



    public Book createBook(BookCreateRequest bookCreateRequest) {
        Author author = bookCreateRequest.toAuthor();
        author = authorService.createOrGetAuthor(author);

        Book book = bookCreateRequest.to();
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public Book createOrUpdateBook(Book book){
        return bookRepository.save(book);
    }

    public List<BookResponse> getBookById(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        return Collections.singletonList(book.to());
    }

    public List<BookResponse> getBookByGenre(String genre){
        return bookRepository.getBooksInGenre(Genre.valueOf(genre)).stream()
                .map(Book::to)
                .collect(Collectors.toList());
    }

    public List<BookResponse> getBookByAuthorEmail(String authorEmail){
        return null;
    }

    public List<BookResponse> getBookByAvail(boolean avail){
        return null;
    }
}
