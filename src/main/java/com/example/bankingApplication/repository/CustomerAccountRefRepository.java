package com.example.bankingApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankingApplication.Model.CustomerAccountRef;

@Repository
public interface CustomerAccountRefRepository extends JpaRepository<CustomerAccountRef, String> {

}