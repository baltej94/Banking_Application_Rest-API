package com.example.bankingApplication.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankingApplication.Model.Account;
import com.example.bankingApplication.Model.Contact;
import com.example.bankingApplication.Model.Customer;
import com.example.bankingApplication.Model.CustomerAccountRef;
import com.example.bankingApplication.Model.Transaction;
import com.example.bankingApplication.domain.AccountInformation;
import com.example.bankingApplication.domain.CustomerDetails;
import com.example.bankingApplication.domain.DepositDetails;
import com.example.bankingApplication.domain.TransactionDetails;
import com.example.bankingApplication.domain.TransferDetails;
import com.example.bankingApplication.domain.WithdrawDetails;
import com.example.bankingApplication.repository.AccountRepository;
import com.example.bankingApplication.repository.CustomerAccountRefRepository;
import com.example.bankingApplication.repository.CustomerRepository;
import com.example.bankingApplication.repository.TransactionRepository;
import com.example.bankingApplication.service.helper.BankingServiceHelper;

@Service
@Transactional
public class BankingServiceImpl implements BankingService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BankingServiceHelper bankingServiceHelper;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerAccountRefRepository custAccRefRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public ResponseEntity<List<CustomerDetails>> findAll() {
		// TODO Auto-generated method stub
		List<CustomerDetails> allCustomerDetails = new ArrayList<>();

		Iterable<Customer> customerList = customerRepository.findAll();

		customerList.forEach(customer -> {
			allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(allCustomerDetails);

		//		return ResponseEntity.status(HttpStatus.CREATED).body(allCustomerDetails);
	}

	@Override
	public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {
		// TODO Auto-generated method stub
		Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		//System.out.println("customer----"+customer);
		customerRepository.save(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
	}

	@Override
	public ResponseEntity<Object> findByCustomerNumber(Long customerNumber) {
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(customerEntityOpt.isPresent())
			return ResponseEntity.status(HttpStatus.CREATED).body(bankingServiceHelper.convertToCustomerDomain(customerEntityOpt.get()));

		return null;
	}

	@Override
	public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber) {
		Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
		Customer unmanagedCustomerEntity = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		if(managedCustomerEntityOpt.isPresent()) {
			Customer managedCustomerEntity = managedCustomerEntityOpt.get();

			if(Optional.ofNullable(unmanagedCustomerEntity.getContactDetails()).isPresent()) {

				Contact managedContact = managedCustomerEntity.getContactDetails();
				if(managedContact != null) {
					managedContact.setEmailId(unmanagedCustomerEntity.getContactDetails().getEmailId());
					managedContact.setHomePhone(unmanagedCustomerEntity.getContactDetails().getHomePhone());
					managedContact.setWorkPhone(unmanagedCustomerEntity.getContactDetails().getWorkPhone());
				} else
					managedCustomerEntity.setContactDetails(unmanagedCustomerEntity.getContactDetails());
			}


			managedCustomerEntity.setStatus(unmanagedCustomerEntity.getStatus());
			managedCustomerEntity.setFirstName(unmanagedCustomerEntity.getFirstName());
			managedCustomerEntity.setLastName(unmanagedCustomerEntity.getLastName());

			customerRepository.save(managedCustomerEntity);

			return ResponseEntity.status(HttpStatus.OK).body("Success: Customer updated.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}
	}

	@Override
	public ResponseEntity<Object> deleteCustomer(Long customerNumber) {
		// TODO Auto-generated method stub
		Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(managedCustomerEntityOpt.isPresent()) {
			Customer managedCustomerEntity = managedCustomerEntityOpt.get();
			customerRepository.delete(managedCustomerEntity);
			return ResponseEntity.status(HttpStatus.OK).body("Success: Customer deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not exist.");
		}

	}

	@Override
	public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber) {
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(customerEntityOpt.isPresent()) {
			Account account = (Account) bankingServiceHelper.convertToAccountEntity(accountInformation);
			System.out.println("Customer Exists----");

			System.out.println("accountInformation----"+accountInformation);

			accountRepository.save(account);
			// Add an entry to the CustomerAccountXRef
			custAccRefRepository.save(CustomerAccountRef.builder()
					.accountNumber(accountInformation.getAccountNumber())
					.customerNumber(customerNumber)
					.build());

		}

		return ResponseEntity.status(HttpStatus.CREATED).body("New Account created successfully.");
	}

	@Override
	public ResponseEntity<Object> findByAccountNumber(Long accountNumber) {
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);

		if(accountEntityOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(bankingServiceHelper.convertToAccountDomain(accountEntityOpt.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number " + accountNumber + " not found.");
		}
	}


	@Override
	public ResponseEntity<Object> DepositToAccount(DepositDetails depositDetails, Long customerNumber) {
		System.out.println("depositDeatails---------"+depositDetails.getDepositAmount());
		System.out.println("depositDeatails---------"+depositDetails.getAccountNumber());
		// TODO Auto-generated method stub
		Account AccountEntity = null;
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		// If customer is present
		if(customerEntityOpt.isPresent()) {
			//System.out.println("account number-------"+depositDetails.getAccountNumber());
			// get ACCOUNT info
			Optional<Account> AccountEntityOpt = accountRepository.findByAccountNumber(depositDetails.getAccountNumber());
			System.out.println("AccountEntityOpt---------"+AccountEntityOpt);
			if(AccountEntityOpt.isPresent()) {

				AccountEntity = AccountEntityOpt.get();

				synchronized (this) {
					AccountEntity.setAccountBalance(AccountEntity.getAccountBalance()+ depositDetails.getDepositAmount());



					accountRepository.save(AccountEntity);


					// Create transaction for Account
					Transaction toTransaction = bankingServiceHelper.createDepositTransaction(depositDetails, AccountEntity.getAccountNumber(), "DEPOSIT");
					transactionRepository.save(toTransaction);
				}
				return ResponseEntity.status(HttpStatus.OK).body("Success: Amount Deposited for Customer Number " + customerNumber);
			}
			else {
				// if from request does not exist, Bad Request
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Account Number " + depositDetails.getAccountNumber() + " not found.");
			}


		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}

	}

	@Override
	public ResponseEntity<Object> WithDrawFromAccount(WithdrawDetails withdrawDeatails, Long customerNumber) {
		// TODO Auto-generated method stub
		Account AccountEntity = null;
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		// If customer is present
		if(customerEntityOpt.isPresent()) {
			System.out.println("account number-------"+withdrawDeatails.getAccountNumber());
			// get ACCOUNT info
			Optional<Account> AccountEntityOpt = accountRepository.findByAccountNumber(withdrawDeatails.getAccountNumber());
			System.out.println("AccountEntityOpt---------"+AccountEntityOpt);
			if(AccountEntityOpt.isPresent()) {

				AccountEntity = AccountEntityOpt.get();

				synchronized (this) {
					AccountEntity.setAccountBalance(AccountEntity.getAccountBalance()- withdrawDeatails.getWithdrawlAmount());



					accountRepository.save(AccountEntity);


					// Create transaction for Account
					Transaction toTransaction = bankingServiceHelper.createWithdrawTransaction(withdrawDeatails, AccountEntity.getAccountNumber(), "WITHDRAWL");
					transactionRepository.save(toTransaction);
				}
				return ResponseEntity.status(HttpStatus.OK).body("Success: Amount Withdrawed for Customer Number " + customerNumber);
			}
			else {
				// if from request does not exist, Bad Request
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Account Number " + withdrawDeatails.getAccountNumber() + " not found.");
			}


		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}
	}


	@Override
	public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber) {

		List<Account> accountEntities = new ArrayList<>();
		Account fromAccountEntity = null;
		Account toAccountEntity = null;

		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		// If customer is present
		if(customerEntityOpt.isPresent()) {

			// get FROM ACCOUNT info
			Optional<Account> fromAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getFromAccountNumber());
			if(fromAccountEntityOpt.isPresent()) {
				fromAccountEntity = fromAccountEntityOpt.get();
			}
			else {
				// if from request does not exist, Bad Request
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + transferDetails.getFromAccountNumber() + " not found.");
			}


			// get TO ACCOUNT info
			Optional<Account> toAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getToAccountNumber());
			if(toAccountEntityOpt.isPresent()) {
				toAccountEntity = toAccountEntityOpt.get();
			}
			else {
				// if from request does not exist, Bad Request
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("To Account Number " + transferDetails.getToAccountNumber() + " not found.");
			}


			// if not sufficient funds, return 400 Bad Request
			if(fromAccountEntity.getAccountBalance() < transferDetails.getTransferAmount()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
			}
			else {
				synchronized (this) {
					// update FROM ACCOUNT 
					fromAccountEntity.setAccountBalance(fromAccountEntity.getAccountBalance() - transferDetails.getTransferAmount());
					fromAccountEntity.setUpdateDateTime(new Date());
					accountEntities.add(fromAccountEntity);

					// update TO ACCOUNT
					toAccountEntity.setAccountBalance(toAccountEntity.getAccountBalance() + transferDetails.getTransferAmount());
					toAccountEntity.setUpdateDateTime(new Date());
					accountEntities.add(toAccountEntity);

					accountRepository.saveAll(accountEntities);

					// Create transaction for FROM Account
					Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetails, fromAccountEntity.getAccountNumber(), "DEBIT");
					transactionRepository.save(fromTransaction);

					// Create transaction for TO Account
					Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetails, toAccountEntity.getAccountNumber(), "CREDIT");
					transactionRepository.save(toTransaction);
				}

				return ResponseEntity.status(HttpStatus.OK).body("Success: Amount transferred for Customer Number " + customerNumber);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}

	}

	@Override
	public ResponseEntity<List<TransactionDetails>> findTransactionsByAccountNumber(Long accountNumber) {
		List<TransactionDetails> transactionDetails = new ArrayList<>();
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntityOpt.isPresent()) {
			Optional<List<Transaction>> transactionEntitiesOpt = transactionRepository.findByAccountNumber(accountNumber);
			if(transactionEntitiesOpt.isPresent()) {
				transactionEntitiesOpt.get().forEach(transaction -> {
					transactionDetails.add(bankingServiceHelper.convertToTransactionDomain(transaction));
				});
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionDetails);

		//return transactionDetails;
	}

}

