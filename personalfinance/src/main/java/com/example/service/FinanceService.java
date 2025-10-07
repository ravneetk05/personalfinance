package com.example.service;

import com.example.model.Account;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public User createUser(String name, String email) {
        User user = new User(name, email);
        return userRepository.save(user);
    }

    public Account createAccount(String accountName, double initialBalance, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Account account = new Account(accountName, initialBalance, user.get());
            return accountRepository.save(account);
        }
        return null;
    }

    public Transaction addTransaction(Long accountId, double amount, String type, String note) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();

            // Update balance
            if ("CREDIT".equalsIgnoreCase(type)) {
                account.setBalance(account.getBalance() + amount);
            } else if ("DEBIT".equalsIgnoreCase(type)) {
                account.setBalance(account.getBalance() - amount);
            }

            accountRepository.save(account);

            Transaction transaction = new Transaction(amount, type.toUpperCase(), note, account);
            return transactionRepository.save(transaction);
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Account> getAccountsByUser(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public double getAccountBalance(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.map(Account::getBalance).orElse(0.0);
    }
}