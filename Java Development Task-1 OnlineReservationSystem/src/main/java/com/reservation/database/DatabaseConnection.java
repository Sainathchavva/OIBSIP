package com.reservation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:reservation.db";

    public static Connection connect() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }

        return connection;
    }
}