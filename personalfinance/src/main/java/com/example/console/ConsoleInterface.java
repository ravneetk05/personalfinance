package com.example.console;

import com.example.model.Account;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleInterface implements CommandLineRunner {

    @Autowired
    private FinanceService financeService;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Personal Finance Tracker ===");
        System.out.println("Welcome to your Personal Finance Tracker!");

        while (true) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    addTransaction();
                    break;
                case 4:
                    viewAccounts();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    checkBalance();
                    break;
                case 0:
                    System.out.println("Thank you for using Personal Finance Tracker!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Create User");
        System.out.println("2. Create Account");
        System.out.println("3. Add Transaction");
        System.out.println("4. View Accounts");
        System.out.println("5. View Transactions");
        System.out.println("6. Check Balance");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void createUser() {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        User user = financeService.createUser(name, email);
        System.out.println("User created successfully! ID: " + user.getId());
    }

    private void createAccount() {
        List<User> users = financeService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found. Please create a user first.");
            return;
        }

        System.out.println("Available Users:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }

        System.out.print("Enter User ID: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter account name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = Double.parseDouble(scanner.nextLine());

        Account account = financeService.createAccount(accountName, initialBalance, userId);
        if (account != null) {
            System.out.println("Account created successfully! ID: " + account.getId());
        } else {
            System.out.println("Failed to create account. Invalid user ID.");
        }
    }

    private void addTransaction() {
        List<User> users = financeService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found. Please create a user and account first.");
            return;
        }

        System.out.println("Select User:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }
        System.out.print("Enter User ID: ");
        Long userId = Long.parseLong(scanner.nextLine());

        List<Account> accounts = financeService.getAccountsByUser(userId);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this user.");
            return;
        }

        System.out.println("Available Accounts:");
        for (Account account : accounts) {
            System.out.println("ID: " + account.getId() + ", Name: " + account.getName() +
                    ", Balance: $" + String.format("%.2f", account.getBalance()));
        }

        System.out.print("Enter Account ID: ");
        Long accountId = Long.parseLong(scanner.nextLine());
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter type (CREDIT/DEBIT): ");
        String type = scanner.nextLine().toUpperCase();
        System.out.print("Enter note: ");
        String note = scanner.nextLine();

        Transaction transaction = financeService.addTransaction(accountId, amount, type, note);
        if (transaction != null) {
            System.out.println("Transaction added successfully! ID: " + transaction.getId());
            System.out.println("New balance: $" + String.format("%.2f",
                    financeService.getAccountBalance(accountId)));
        } else {
            System.out.println("Failed to add transaction. Invalid account ID.");
        }
    }

    private void viewAccounts() {
        List<User> users = financeService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            System.out.println("\n=== User: " + user.getName() + " ===");
            List<Account> accounts = financeService.getAccountsByUser(user.getId());
            if (accounts.isEmpty()) {
                System.out.println("No accounts found.");
            } else {
                for (Account account : accounts) {
                    System.out.println("Account: " + account.getName() +
                            " | Balance: $" + String.format("%.2f", account.getBalance()));
                }
            }
        }
    }

    private void viewTransactions() {
        List<User> users = financeService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("Select User:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }
        System.out.print("Enter User ID: ");
        Long userId = Long.parseLong(scanner.nextLine());

        List<Account> accounts = financeService.getAccountsByUser(userId);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("Select Account:");
        for (Account account : accounts) {
            System.out.println("ID: " + account.getId() + ", Name: " + account.getName());
        }
        System.out.print("Enter Account ID: ");
        Long accountId = Long.parseLong(scanner.nextLine());

        List<Transaction> transactions = financeService.getTransactionsByAccount(accountId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\n=== Transactions ===");
            for (Transaction transaction : transactions) {
                System.out.println("ID: " + transaction.getId() +
                        " | Type: " + transaction.getType() +
                        " | Amount: $" + String.format("%.2f", transaction.getAmount()) +
                        " | Note: " + transaction.getNote());
            }
        }
    }

    private void checkBalance() {
        List<User> users = financeService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("Select User:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName());
        }
        System.out.print("Enter User ID: ");
        Long userId = Long.parseLong(scanner.nextLine());

        List<Account> accounts = financeService.getAccountsByUser(userId);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("Select Account:");
        for (Account account : accounts) {
            System.out.println("ID: " + account.getId() + ", Name: " + account.getName());
        }
        System.out.print("Enter Account ID: ");
        Long accountId = Long.parseLong(scanner.nextLine());

        double balance = financeService.getAccountBalance(accountId);
        System.out.println("Current Balance: $" + String.format("%.2f", balance));
    }
}