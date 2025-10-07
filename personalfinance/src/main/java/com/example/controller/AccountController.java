package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepo;

    public AccountController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @PostMapping
    public Account addAccount(@RequestBody Account account) {
        return accountRepo.save(account);
    }

    @GetMapping
    public List<Account> getAll() {
        return accountRepo.findAll();
    }
}