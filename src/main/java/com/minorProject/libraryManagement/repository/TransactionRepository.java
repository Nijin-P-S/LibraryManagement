package com.minorProject.libraryManagement.repository;

import com.minorProject.libraryManagement.models.Transaction;
import com.minorProject.libraryManagement.models.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findTransactionsByRequest_Book_IdAndTransactionStatusOrderByTransactionDateDesc(
            int bookId, TransactionStatus transactionStatus);

//    List<Transaction> findTransactionsByRequest_Book_IdAndTransactionStatus(int bookId, TransactionStatus transactionStatus);

}
