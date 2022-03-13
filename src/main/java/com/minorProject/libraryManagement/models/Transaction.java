package com.minorProject.libraryManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalTransactionId;  //UUID

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties("transaction")
    private Request request;

    @CreationTimestamp
    private Date transactionDate;

//    @Enumerated(value=EnumType.STRING)
//    private TransactionType transactionType;

    @Enumerated(value=EnumType.STRING)
    private TransactionStatus transactionStatus;

    private Double fine;
}