package com.minorProject.libraryManagement.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minorProject.libraryManagement.models.Author;
import com.minorProject.libraryManagement.models.Genre;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private int id;

    private String name;

    private Genre genre;

    @JsonIgnoreProperties("bookList")
    private Author author;

}
