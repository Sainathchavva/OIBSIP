package com.reservation.ui;

import com.reservation.dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    private final UserDAO userDAO;

    public LoginFrame() {

        userDAO = new UserDAO();

        setTitle("Online Reservation System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createLoginUI();

        setVisible(true);
    }

    private void createLoginUI() {

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 35, 25, 35
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel =
                new JLabel("ONLINE RESERVATION SYSTEM");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel usernameLabel =
                new JLabel("Username:");

        gbc.gridx = 0;
        gbc.gridy = 1;

        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(18);

        gbc.gridx = 1;

        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel =
                new JLabel("Password:");

        gbc.gridx = 0;
        gbc.gridy = 2;

        mainPanel.add(passwordLabel, gbc);

        passwordField =
                new JPasswordField(18);

        gbc.gridx = 1;

        mainPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        gbc.gridx = 0;
        gbc.gridy = 3;

        mainPanel.add(loginButton, gbc);

        gbc.gridx = 1;

        mainPanel.add(signupButton, gbc);

        loginButton.addActionListener(
                e -> login()
        );

        signupButton.addActionListener(
                e -> signup()
        );

        add(mainPanel);
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean valid =
                userDAO.validateUser(
                        username,
                        password
                );

        if (valid) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Open reservation screen
            new ReservationFrame();

            // Close login screen
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void signup() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean registered =
                userDAO.registerUser(
                        username,
                        password
                );

        if (registered) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            usernameField.setText("");
            passwordField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Username already exists or registration failed.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}