package com.reservation.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                )
                """;

        String createTrainsTable = """
                CREATE TABLE IF NOT EXISTS trains (
                    train_number INTEGER PRIMARY KEY,
                    train_name TEXT NOT NULL
                )
                """;

        String createReservationsTable = """
                CREATE TABLE IF NOT EXISTS reservations (
                    pnr INTEGER PRIMARY KEY AUTOINCREMENT,
                    passenger_name TEXT NOT NULL,
                    train_number INTEGER NOT NULL,
                    train_name TEXT NOT NULL,
                    class_type TEXT NOT NULL,
                    journey_date TEXT NOT NULL,
                    source TEXT NOT NULL,
                    destination TEXT NOT NULL
                )
                """;

        try (Connection connection = DatabaseConnection.connect()) {

            if (connection == null) {
                System.out.println("Database initialization failed.");
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(createUsersTable)) {
                statement.executeUpdate();
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(createTrainsTable)) {
                statement.executeUpdate();
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(createReservationsTable)) {
                statement.executeUpdate();
            }

            System.out.println("Database tables created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating database tables.");
            e.printStackTrace();
        }
    }
}