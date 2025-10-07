package com.example.controller;

import com.example.model.Account;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private FinanceService financeService;

    @GetMapping("/")
    public String home(Model model) {
        List<User> users = financeService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/create-user")
    public String createUserForm() {
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestParam String name, @RequestParam String email) {
        financeService.createUser(name, email);
        return "redirect:/";
    }

    @GetMapping("/create-account")
    public String createAccountForm(Model model) {
        List<User> users = financeService.getAllUsers();
        model.addAttribute("users", users);
        return "create-account";
    }

    @PostMapping("/create-account")
    public String createAccount(@RequestParam String accountName,
            @RequestParam double initialBalance,
            @RequestParam Long userId) {
        financeService.createAccount(accountName, initialBalance, userId);
        return "redirect:/";
    }

    @GetMapping("/add-transaction")
    public String addTransactionForm(Model model) {
        List<User> users = financeService.getAllUsers();
        model.addAttribute("users", users);
        return "add-transaction";
    }

    @PostMapping("/add-transaction")
    public String addTransaction(@RequestParam Long accountId,
            @RequestParam double amount,
            @RequestParam String type,
            @RequestParam String note) {
        financeService.addTransaction(accountId, amount, type, note);
        return "redirect:/";
    }

    @GetMapping("/accounts/{userId}")
    public String viewAccounts(@PathVariable Long userId, Model model) {
        List<Account> accounts = financeService.getAccountsByUser(userId);
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/transactions/{accountId}")
    public String viewTransactions(@PathVariable Long accountId, Model model) {
        List<Transaction> transactions = financeService.getTransactionsByAccount(accountId);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

    @GetMapping("/api/accounts/{userId}")
    @ResponseBody
    public List<Account> getAccountsForUser(@PathVariable Long userId) {
        return financeService.getAccountsByUser(userId);
    }
}