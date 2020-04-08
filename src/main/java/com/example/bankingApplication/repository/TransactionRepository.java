package com.example.bankingApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bankingApplication.Model.Transaction;


import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    public Optional<List<Transaction>> findByAccountNumber(Long accountNumber);
    
}
