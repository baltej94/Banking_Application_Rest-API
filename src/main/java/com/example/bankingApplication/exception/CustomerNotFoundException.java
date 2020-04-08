package com.example.bankingApplication.exception;

public class CustomerNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int customerId;
	public CustomerNotFoundException(Long customerId) {
	        super(String.format("No Customer found with id : '%s'", customerId));
	        }
}
