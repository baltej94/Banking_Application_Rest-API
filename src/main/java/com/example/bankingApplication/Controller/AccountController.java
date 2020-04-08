package com.example.bankingApplication.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingApplication.domain.AccountInformation;
import com.example.bankingApplication.domain.DepositDetails;
import com.example.bankingApplication.domain.TransactionDetails;
import com.example.bankingApplication.domain.TransferDetails;
import com.example.bankingApplication.domain.WithdrawDetails;
import com.example.bankingApplication.service.BankingServiceImpl;

@RestController
public class AccountController {

	@Autowired
	private BankingServiceImpl bankingService;

	@RequestMapping(value = "/addCustomerAccount/{customerNumber}", method = RequestMethod.POST)  
	public ResponseEntity<Object> addNewAccount(@RequestBody AccountInformation accountInformation,
			@PathVariable Long customerNumber) {
		System.out.println("accountInformation----"+accountInformation);
		return bankingService.addNewAccount(accountInformation, customerNumber);
	}


	@GetMapping(path = "/getAccount/{accountNumber}")
	public ResponseEntity<Object> getByAccountNumber(@PathVariable Long accountNumber) {

		return bankingService.findByAccountNumber(accountNumber);
	}

	@RequestMapping(value = "/depositFunds/{customerNumber}", method = RequestMethod.POST)  
	public ResponseEntity<Object> depositFunds(@RequestBody DepositDetails depositDetails,
			@PathVariable Long customerNumber) {
		return bankingService.DepositToAccount(depositDetails, customerNumber);
	}

	@RequestMapping(value = "/withdrawFunds/{customerNumber}", method = RequestMethod.POST)  
	public ResponseEntity<Object> withdrawFunds(@RequestBody WithdrawDetails withdrawDetails,
			@PathVariable Long customerNumber) {
		return bankingService.WithDrawFromAccount(withdrawDetails, customerNumber);
	}

	//@PutMapping(path = "/transferFunds/{customerNumber}")
	@RequestMapping(value = "/transferFunds/{customerNumber}", method = RequestMethod.POST)  
	public ResponseEntity<Object> transferDetails(@RequestBody TransferDetails transferDetails,
			@PathVariable Long customerNumber) {
		return bankingService.transferDetails(transferDetails, customerNumber);
	}

	@GetMapping(path = "/getTransactions/{accountNumber}")
	public ResponseEntity<List<TransactionDetails>> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

		return bankingService.findTransactionsByAccountNumber(accountNumber);
	}



}
