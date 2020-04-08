package com.example.bankingApplication.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

import com.example.bankingApplication.domain.CustomerDetails;
import com.example.bankingApplication.service.BankingServiceImpl;

@RestController
public class CustomerController {

	@Autowired
	private BankingServiceImpl bankingService;


	@GetMapping("/home")
	public String home() {
		return "home.jsp";
	}



	@GetMapping(path = "/allCustomers")

	public ResponseEntity<List<CustomerDetails>> getAllCustomers() {

		return bankingService.findAll();
	}

	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST)  
	public ResponseEntity<Object> createCustomer(@Valid CustomerDetails customer) {

		return bankingService.addCustomer(customer);
	}


	@GetMapping(path = "/getCustomer/{customerNumber}")

	public ResponseEntity<Object> getCustomer(@PathVariable Long customerNumber) {
		return bankingService.findByCustomerNumber(customerNumber);

		//eturn bankingService.findByCustomerNumber(customerNumber);
	}

	@RequestMapping(value = "/updateCustomer/{customerNumber}", method = RequestMethod.POST)  

	public ResponseEntity<Object> updateCustomer(@Valid CustomerDetails customerDetails,
			@PathVariable Long customerNumber) {

		return bankingService.updateCustomer(customerDetails, customerNumber);
	}

	@RequestMapping(path = "/deleteCustomer/{customerNumber}", method = RequestMethod.POST)

	public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerNumber) {

		return bankingService.deleteCustomer(customerNumber);
	}

}
