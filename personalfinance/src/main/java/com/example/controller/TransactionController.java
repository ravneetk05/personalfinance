package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.model.Transaction;
import com.example.model.Account;
import com.example.repository.TransactionRepository;
import com.example.repository.AccountRepository;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;

    public TransactionController(TransactionRepository txRepo, AccountRepository accountRepo) {
        this.txRepo = txRepo;
        this.accountRepo = accountRepo;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction tx) {
        // update balance
        Account acc = accountRepo.findById(tx.getAccount().getId()).orElseThrow();
        if (tx.getType().equalsIgnoreCase("DEBIT")) {
            acc.setBalance(acc.getBalance() - tx.getAmount());
        } else {
            acc.setBalance(acc.getBalance() + tx.getAmount());
        }
        accountRepo.save(acc);
        return txRepo.save(tx);
    }

    @GetMapping
    public List<Transaction> getAll() {
        return txRepo.findAll();
    }
}