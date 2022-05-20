package com.minorProject.libraryManagement;

import com.minorProject.libraryManagement.Requests.CreateAdminRequest;
import com.minorProject.libraryManagement.models.Transaction;
import com.minorProject.libraryManagement.models.TransactionStatus;
import com.minorProject.libraryManagement.repository.TransactionRepository;
import com.minorProject.libraryManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner  {
//
//	@Autowired
//	TransactionRepository transactionRepository;

	@Autowired
	AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CreateAdminRequest createAdminRequest = CreateAdminRequest.builder()
				.name("Nijin")
				.email("nijin@gmail.com")
				.username("iamnijin")
				.password("nijin123")
				.build();
		adminService.createAdmin(createAdminRequest);
	}

//	@Override
//	void run(String... args) throws Exception{
//		List<Transaction> transactionList= transactionRepository
//				.findTransactionsByRequestBook_IdAndTransactionStatusOrderByTransactionDateDesc(
//				1, TransactionStatus.SUCCESS);

//		List<Transaction> transactionList = transactionRepository.findTransactionsByRequest_Book_IdAndTransactionStatus
//				(1,TransactionStatus.SUCCESS);
//
//		for(Transaction transaction: transactionList){
//			System.out.println(transaction.getId());
//		}
//	}
}
