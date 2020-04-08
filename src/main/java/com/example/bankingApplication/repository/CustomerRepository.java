package com.example.bankingApplication.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankingApplication.Model.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
    public Optional<Customer> findByCustomerNumber(Long customerNumber);

}