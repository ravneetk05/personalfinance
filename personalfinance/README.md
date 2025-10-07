# Personal Finance Tracker

## Features

Your Personal Finance Tracker now has two interfaces:

### 1. Console Interface (Command Line)

- Automatically starts when you run the application
- Interactive menu-driven interface
- Perfect for quick data entry and checks

### 2. Web Interface (Browser)

- Modern web UI with forms and views
- Visual dashboard with cards and styling
- Easy to use forms for creating users, accounts, and transactions

## How to Run

### Start the Application

```bash
cd "/Users/rishu/Desktop/personal finanace tracker/personalfinance"
mvn spring-boot:run
```

### Console Interface

When you start the application, you'll see a console menu:

```
=== Personal Finance Tracker ===
Welcome to your Personal Finance Tracker!

=== Main Menu ===
1. Create User
2. Create Account
3. Add Transaction
4. View Accounts
5. View Transactions
6. Check Balance
0. Exit
```

### Web Interface

Open your web browser and go to: `http://localhost:8080`

You'll see a dashboard with:

- Home page showing all users
- Create User form
- Create Account form
- Add Transaction form
- View accounts and transactions

## Usage Flow

### First Time Setup:

1. **Create a User** (either via console or web)
   - Enter name and email
2. **Create an Account**

   - Select the user
   - Enter account name (e.g., "Checking Account")
   - Enter initial balance

3. **Add Transactions**
   - Select user and account
   - Choose CREDIT (money in) or DEBIT (money out)
   - Enter amount and description

### Features:

- **Automatic Balance Updates**: Balances update automatically when you add transactions
- **Multiple Users**: Support for multiple users and accounts
- **Transaction History**: View all transactions for any account
- **Real-time Data**: Both console and web interfaces work with the same database

## Database

- Uses MySQL database `pft_db`
- Automatically creates tables on first run
- Data persists between sessions

## Tips:

- Use the web interface for a better visual experience
- Use the console interface for quick data entry
- Both interfaces work simultaneously - you can switch between them
- Your data is automatically saved to the MySQL database

Enjoy tracking your finances! 💰📊
