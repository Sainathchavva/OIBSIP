# ATM Interface – Java Console Application

## Project Overview

The ATM Interface is a console-based Java application that simulates the basic operations of an ATM machine.

The application allows a user to log in using a User ID and PIN and perform common banking operations such as checking the transaction history, withdrawing money, depositing money, transferring money, and checking the account balance.

The project was developed using Java and Object-Oriented Programming concepts.

## Technologies Used

* Java
* Eclipse IDE
* Java Collections Framework
* ArrayList
* HashMap
* Object-Oriented Programming
* Console Input/Output

## Features

* User ID and PIN authentication
* Maximum of three login attempts
* Access denial after three incorrect attempts
* Transaction history
* Cash withdrawal
* Cash deposit
* Account-to-account money transfer
* Balance validation
* Insufficient funds validation
* Account balance checking
* Transaction storage using ArrayList
* Multiple classes following OOP principles
* Console-based menu system

## Project Structure

```text
ATMInterface
│
├── src
│   └── atm
│       ├── Main.java
│       ├── ATM.java
│       ├── Account.java
│       ├── Transaction.java
│       ├── Bank.java
│       └── User.java
│
└── README.md
```

## Class Description

### Main.java

This is the entry point of the application.

It creates the Bank object, creates sample accounts, adds them to the bank, creates the ATM object, and starts the application.

### ATM.java

This class controls the main ATM operations.

It handles:

* Login
* Authentication attempts
* ATM menu
* Withdrawal
* Deposit
* Transfer
* Transaction history
* Balance display
* Logout/exit

### Account.java

This class represents a bank account.

It stores:

* Account ID
* Account holder name
* Account balance
* Transaction history

It also provides methods for depositing and withdrawing money.

### Transaction.java

This class represents an individual banking transaction.

It stores:

* Transaction type
* Amount
* Transaction details
* Date and time

### Bank.java

The Bank class maintains the available accounts using a HashMap.

It provides methods to add accounts and find an account using its account ID.

### User.java

The User class demonstrates basic encapsulation for storing user ID and PIN information.

## Sample Login Details

| User ID | PIN  | Initial Balance |
| ------- | ---- | --------------: |
| 1001    | 1234 |         ₹10,000 |
| 1002    | 5678 |          ₹7,500 |

These credentials are provided only for testing the application.

## How to Run

1. Open Eclipse.
2. Create a Java Project named `ATMInterface`.
3. Create the package `atm`.
4. Create all six Java classes.
5. Add the corresponding source code.
6. Run `Main.java` as a Java Application.
7. Enter one of the sample login credentials.
8. Select an operation from the ATM menu.

## Example Menu

```text
========================================
              ATM MENU
========================================
1. Transaction History
2. Withdraw
3. Deposit
4. Transfer
5. Check Balance
6. Quit
========================================
```

## OOP Concepts Used

### Encapsulation

The class fields are declared as private and are accessed through public methods.

### Classes and Objects

The application is divided into multiple classes representing ATM, Bank, Account, Transaction, and User.

### Abstraction

The ATM class provides methods for performing banking operations without exposing all implementation details to the user.

### Collections

`ArrayList` is used to maintain transaction history.

`HashMap` is used by the Bank class to manage accounts.

### Methods

Separate methods are used for operations such as login, withdrawal, deposit, transfer, and transaction history.

## Validation

The application performs several validations:

* Invalid User ID or PIN
* Maximum three login attempts
* Invalid menu selection
* Invalid amount
* Zero or negative amount
* Insufficient balance
* Invalid recipient account
* Transfer to the same account

## Limitations

This is an educational console application. It does not use a real database or connect to an actual banking system.

Account information and sample credentials are stored in the application itself, so data is not persistent between program executions.

## Learning Outcomes

Through this project, the following Java concepts can be practiced:

* Object-Oriented Programming
* Encapsulation
* Classes and objects
* Constructors
* ArrayList
* HashMap
* Exception handling
* Switch-case
* Loops
* Methods
* User input using Scanner
* Basic transaction processing

## Future Improvements

Possible improvements include:

* Database integration using JDBC
* Password/PIN encryption
* GUI using Java Swing or JavaFX
* Persistent transaction history
* Multiple ATM users
* Account statement generation
* Database-based authentication
* Improved security and session management
