package com.minorProject.libraryManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request implements Serializable {//Made serializable because Request is child object in Student and is not primitive datatype
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String requestId;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("requestsCreated")
    private Student student;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("requestsToProcess")
    private Admin admin;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("requestList")
    private Book book;

    @OneToOne(mappedBy = "request")
    @JsonIgnoreProperties("request")
    private Transaction transaction;

    @CreationTimestamp
    private Date requestDate;

    @Enumerated(value=EnumType.STRING)
    private RequestStatus requestStatus;

    @Enumerated(value = EnumType.STRING)
    private RequestType requestType;

    private String adminComment;

    private String systemComment;
}
