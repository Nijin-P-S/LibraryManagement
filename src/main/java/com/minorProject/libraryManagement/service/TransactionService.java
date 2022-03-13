package com.minorProject.libraryManagement.service;

import com.minorProject.libraryManagement.models.*;
import com.minorProject.libraryManagement.repository.BookRepository;
import com.minorProject.libraryManagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    RequestService requestService;

    @Autowired
    BookService bookService;

    //For Autowiring the application properties defined
    @Value("${book.allocated.max_days}")
    int allottedDays;

    @Value("${book.fine.per_day}")
    int finePerDay;


    public String createTransaction(int requestId) throws Exception {
        Request request = Request.builder().id(requestId).build();
        return createTransaction(request);
    }

    public String createTransaction(Request request) throws Exception {
        Transaction transaction = Transaction.builder()
                .externalTransactionId(UUID.randomUUID().toString())
                .request(request)
                .transactionStatus(TransactionStatus.PENDING)
                .fine(calculateFine(request))
                .build();
        try {
            Transaction savedTxn = transactionRepository.save(transaction);

            if (request.getBook() == null || request.getStudent() == null) {
                request = requestService.getRequestById(request.getId());
            }

            //Actual Transaction process
            switch (request.getRequestType()) {
                case ISSUE:
                    issueBook(request);
                    break;
                case RETURN:
                    returnBook(request);
                    break;
            }
            savedTxn.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(savedTxn);
        }
        catch (Exception failed){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }
        return transaction.getExternalTransactionId();

    }

    public void issueBook(Request request){
        Book requestedBook = request.getBook();
        Student student = request.getStudent();

        requestedBook.setStudent(student);

        bookService.createOrUpdateBook(requestedBook);
    }

    public void returnBook(Request request){
        Book requestedBook = request.getBook();

        requestedBook.setStudent(null);

        bookService.createOrUpdateBook(requestedBook);
    }

    public Double calculateFine(Request request) throws Exception {
        if(RequestType.ISSUE.equals(request.getRequestType()))
            return null;

        List<Transaction> transactionList =transactionRepository.findTransactionsByRequest_Book_IdAndTransactionStatusOrderByTransactionDateDesc(
                request.getBook().getId(),TransactionStatus.SUCCESS);

        Transaction txn = transactionList.get(0);

        if(!RequestType.ISSUE.equals(txn.getRequest().getRequestType()))
        {
            throw new Exception("Last transaction is not an Issue transaction");
        }

        long timeOfIssueInMillis = txn.getTransactionDate().getTime();
        long timeDiff = System.currentTimeMillis()-timeOfIssueInMillis;
        long noOfDaysPassed = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        double fine = 0.0;

        if(noOfDaysPassed>allottedDays){
            fine+=(noOfDaysPassed-allottedDays)*finePerDay;
        }
        return fine;

    }
}
