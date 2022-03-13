package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.Author;
import com.minorProject.libraryManagement.models.Book;
import com.minorProject.libraryManagement.models.Genre;
import lombok.*;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {

    @NotNull
    private String bookName;

    @NotNull
    private Genre genre;

    @NotNull
    private String authorName;

    @NotNull
    @Email
    private String authorEmail;

    private String authorWebsite;

    public Book to(){
        return Book.builder()
                .name(this.bookName)
                .genre(this.genre)
                .build();
    }

    public Author toAuthor(){
        return Author.builder()
                .email(this.getAuthorEmail())
                .name(this.getAuthorName())
                .website(this.getAuthorWebsite())
                .build();
    }
}
