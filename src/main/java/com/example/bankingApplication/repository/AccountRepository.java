package com.example.bankingApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.bankingApplication.Model.Account;;

@Repository
	public interface AccountRepository extends JpaRepository<Account, String> {

		Optional<Account> findByAccountNumber(Long accountNumber);
	}

