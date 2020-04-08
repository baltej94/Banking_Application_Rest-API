package com.example.bankingApplication.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.bankingApplication.domain.AccountInformation;
import com.example.bankingApplication.domain.CustomerDetails;
import com.example.bankingApplication.domain.DepositDetails;
import com.example.bankingApplication.domain.TransactionDetails;
import com.example.bankingApplication.domain.TransferDetails;
import com.example.bankingApplication.domain.WithdrawDetails;

public interface BankingService {
	
	public ResponseEntity<List<CustomerDetails>> findAll();
    
    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);
    
    public  ResponseEntity<Object>  findByCustomerNumber(Long customerNumber);
    
    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);
    
    public ResponseEntity<Object> deleteCustomer(Long customerNumber) ;
    
   // public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber);
    public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber);

    public ResponseEntity<Object> findByAccountNumber(Long accountNumber);

    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber);
    
    public ResponseEntity<List<TransactionDetails>> findTransactionsByAccountNumber(Long accountNumber);

    public ResponseEntity<Object> DepositToAccount(DepositDetails depositDetails,Long customerNumber);
    
    public ResponseEntity<Object> WithDrawFromAccount(WithdrawDetails withdrawDetails,Long customerNumber);


}
