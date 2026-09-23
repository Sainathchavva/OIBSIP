# Online Reservation System

## Project Overview

The Online Reservation System is a Java-based desktop application developed using Java Swing and SQLite.

The system allows users to create an account, log in securely, book train tickets, generate a unique PNR, view reservation details, and cancel reservations using the PNR.

## Features

- User registration
- Secure password hashing using SHA-256
- User login and authentication
- Train reservation form
- Automatic train name display based on train number
- Passenger details validation
- Train number validation
- Journey date validation
- Automatic PNR generation
- Booking confirmation
- Reservation cancellation using PNR
- Reservation details retrieval
- SQLite database storage
- JDBC database connectivity
- PreparedStatement for database operations
- Java Swing graphical user interface

## Technologies Used

- Java
- Java Swing
- JDBC
- SQLite
- Maven
- IntelliJ IDEA

## Project Structure

```text
OnlineReservationSystem
│
├── src
│   └── main
│       └── java
│           └── com.reservation
│               ├── dao
│               │   ├── ReservationDAO.java
│               │   └── UserDAO.java
│               │
│               ├── database
│               │   ├── DatabaseConnection.java
│               │   └── DatabaseInitializer.java
│               │
│               ├── model
│               │   └── Reservation.java
│               │
│               ├── ui
│               │   ├── CancellationFrame.java
│               │   ├── LoginFrame.java
│               │   └── ReservationFrame.java
│               │
│               └── Main.java
│
├── pom.xml
├── README.md
└── reservation.db