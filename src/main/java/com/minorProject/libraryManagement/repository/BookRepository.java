package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.Book;
import com.minorProject.libraryManagement.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    //1.JPQL - Java Persistance Query Language
    //2.Native Query Language - Queries w.r.t sql tables

    @Query("select b from Book b where b.genre= :genre")
    List<Book> getBooksInGenre(Genre genre);

//    @Query(value= "select * from book b where b.genre= :genre",nativeQuery= true)
//    List<Book> getBooksInGenre(Genre genre);


    //Get Book by Author
    //foreignKey - author_id
    //"select * from book b join author a on b.author_id = a.id where a.email= :email"

    //Get book which are available
    //"select * from book where student_id is null"

}
