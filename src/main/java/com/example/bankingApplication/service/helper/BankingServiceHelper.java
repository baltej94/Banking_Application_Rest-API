package com.example.bankingApplication.service.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.bankingApplication.Model.Account;
import com.example.bankingApplication.Model.BankInfo;
import com.example.bankingApplication.Model.Contact;
import com.example.bankingApplication.Model.Customer;
import com.example.bankingApplication.Model.Transaction;
import com.example.bankingApplication.domain.AccountInformation;
import com.example.bankingApplication.domain.BankInformation;
import com.example.bankingApplication.domain.ContactDetails;
import com.example.bankingApplication.domain.CustomerDetails;
import com.example.bankingApplication.domain.DepositDetails;
import com.example.bankingApplication.domain.TransactionDetails;
import com.example.bankingApplication.domain.TransferDetails;
import com.example.bankingApplication.domain.WithdrawDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Component
public class BankingServiceHelper {

public CustomerDetails convertToCustomerDomain(Customer customer) {
		
		return CustomerDetails.builder()
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.customerNumber(customer.getCustomerNumber())
				.status(customer.getStatus())
				.contactDetails(convertToContactDomain(customer.getContactDetails()))
				.build();
	}

public Customer convertToCustomerEntity(CustomerDetails customerDetails) {
	
	
	return Customer.builder()
			.firstName(customerDetails.getFirstName())
			.lastName(customerDetails.getLastName())
			.customerNumber(customerDetails.getCustomerNumber())
			.status(customerDetails.getStatus())
			.contactDetails(convertToContactEntity(customerDetails.getContactDetails()))
			.build();
}
public Contact convertToContactEntity(ContactDetails contactDetails) {
	
	return Contact.builder()
			.emailId(contactDetails.getEmailId())
			.homePhone(contactDetails.getHomePhone())
			.workPhone(contactDetails.getWorkPhone())
			.build();
}
public ContactDetails convertToContactDomain(Contact contact) {
	
	return ContactDetails.builder()
			.emailId(contact.getEmailId())
			.homePhone(contact.getHomePhone())
			.workPhone(contact.getWorkPhone())
			.build();
}

public Object convertToAccountEntity(AccountInformation accountInformation) {
	return Account.builder()
			.accountType(accountInformation.getAccountType())
			.accountBalance(accountInformation.getAccountBalance())
			.accountNumber(accountInformation.getAccountNumber())
			//.accountStatus(accountInformation.getAccountStatus())
			.bankInformation(convertToBankInfoEntity(accountInformation.getBankInformation()))
			.build();
}
public BankInfo convertToBankInfoEntity(BankInformation bankInformation) {
	
	return BankInfo.builder()
			.branchCode(bankInformation.getBranchCode())
			.branchName(bankInformation.getBranchName())
			.build();
}
public BankInformation convertToBankInfoDomain(BankInfo bankInfo) {
	
	return BankInformation.builder()
			.branchCode(bankInfo.getBranchCode())
			.branchName(bankInfo.getBranchName())
			.build();
}

public AccountInformation convertToAccountDomain(Account account) {

	return AccountInformation.builder()
			.accountType(account.getAccountType())
			.accountBalance(account.getAccountBalance())
			.accountNumber(account.getAccountNumber())
			//.accountStatus(account.getAccountStatus())
			.bankInformation(convertToBankInfoDomain(account.getBankInformation()))
			.build();
}

public Transaction createTransaction(TransferDetails transferDetails, Long accountNumber, String transactionType) {
	return Transaction.builder()
			.accountNumber(accountNumber)
			.txAmount(transferDetails.getTransferAmount())
			.txType(transactionType)
			.txDateTime(new Date())
			.build();
}

public Transaction createDepositTransaction(DepositDetails depositDetails, Long accountNumber, String transactionType) {
	return Transaction.builder()
			.accountNumber(accountNumber)
			.txAmount(depositDetails.getDepositAmount())
			.txType(transactionType)
			.txDateTime(new Date())
			.build();
}



public TransactionDetails convertToTransactionDomain(Transaction transaction) {
	return TransactionDetails.builder()
			.txAmount(transaction.getTxAmount())
			.txDateTime(transaction.getTxDateTime())
			.txType(transaction.getTxType())
			.accountNumber(transaction.getAccountNumber())
			.build();
}

public Transaction createWithdrawTransaction(WithdrawDetails withdrawDeatails, Long accountNumber, String transactionType) {
	return Transaction.builder()
			.accountNumber(accountNumber)
			.txAmount(withdrawDeatails.getWithdrawlAmount())
			.txType(transactionType)
			.txDateTime(new Date())
			.build();
}

	
}
