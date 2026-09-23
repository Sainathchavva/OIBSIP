package com.reservation;

import com.reservation.database.DatabaseInitializer;
import com.reservation.ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting Online Reservation System...");

        DatabaseInitializer.initializeDatabase();

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}