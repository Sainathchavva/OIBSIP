package com.reservation.dao;

import com.reservation.database.DatabaseConnection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean validateUser(String username, String password) {

        String sql = """
                SELECT * FROM users
                WHERE username = ? AND password = ?
                """;

        String hashedPassword = hashPassword(password);

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }

        } catch (SQLException e) {

            System.out.println("Login validation failed.");
            e.printStackTrace();

            return false;
        }
    }

    public boolean registerUser(
            String username,
            String password) {

        String sql = """
                INSERT INTO users (username, password)
                VALUES (?, ?)
                """;

        String hashedPassword =
                hashPassword(password);

        try (Connection connection =
                     DatabaseConnection.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "User registration failed."
            );

            e.printStackTrace();

            return false;
        }
    }

    private String hashPassword(String password) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            password.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hash) {

                String hex =
                        Integer.toHexString(
                                0xff & b
                        );

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(
                    "Password hashing algorithm not available.",
                    e
            );
        }
    }
}