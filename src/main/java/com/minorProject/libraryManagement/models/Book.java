package com.minorProject.libraryManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minorProject.libraryManagement.Responses.BookResponse;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable { //Made serializable because Book is child object in Student and is not primitive datatype
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(value=EnumType.STRING)
    private Genre genre;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("bookList")
    private Author author;

    @JoinColumn
    @ManyToOne
    private Student student;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties("book")
    private List<Request> requestList;

    @CreationTimestamp
    private Date createdOn;

    public BookResponse to(){
        return BookResponse.builder()
                .id(this.getId())
                .name(this.getName())
                .genre(this.getGenre())
                .author(this.getAuthor())
                .build();
    }
}
