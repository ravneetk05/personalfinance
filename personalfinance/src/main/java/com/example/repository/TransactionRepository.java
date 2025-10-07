package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
}