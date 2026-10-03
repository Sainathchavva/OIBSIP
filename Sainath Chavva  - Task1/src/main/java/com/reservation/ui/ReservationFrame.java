package com.reservation.ui;

import com.reservation.dao.ReservationDAO;
import com.reservation.model.Reservation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ReservationFrame extends JFrame {

    private JTextField passengerNameField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JComboBox<String> classTypeComboBox;
    private JTextField journeyDateField;
    private JTextField sourceField;
    private JTextField destinationField;

    private JButton bookButton;
    private JButton cancelButton;
    private JButton cancellationButton;

    private final ReservationDAO reservationDAO;

    private final Map<Integer, String> trains = new HashMap<>();

    public ReservationFrame() {

        reservationDAO = new ReservationDAO();

        loadTrainData();
        createUI();

        setTitle("Online Reservation System - Reservation");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setVisible(true);
    }

    private void loadTrainData() {

        trains.put(12760, "Charminar Express");
        trains.put(12723, "Telangana Express");
        trains.put(12727, "Godavari Express");
        trains.put(17011, "Hyderabad Express");
        trains.put(17015, "Visakha Express");
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new GridBagLayout());

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel =
                new JLabel("TRAIN RESERVATION");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Passenger Name
        gbc.gridx = 0;
        gbc.gridy = 1;

        mainPanel.add(
                new JLabel("Passenger Name:"),
                gbc
        );

        passengerNameField =
                new JTextField(20);

        gbc.gridx = 1;

        mainPanel.add(
                passengerNameField,
                gbc
        );

        // Train Number
        gbc.gridx = 0;
        gbc.gridy = 2;

        mainPanel.add(
                new JLabel("Train Number:"),
                gbc
        );

        trainNumberField =
                new JTextField(20);

        gbc.gridx = 1;

        mainPanel.add(
                trainNumberField,
                gbc
        );

        // Train Name
        gbc.gridx = 0;
        gbc.gridy = 3;

        mainPanel.add(
                new JLabel("Train Name:"),
                gbc
        );

        trainNameField =
                new JTextField(20);

        trainNameField.setEditable(false);

        gbc.gridx = 1;

        mainPanel.add(
                trainNameField,
                gbc
        );

        // Class Type
        gbc.gridx = 0;
        gbc.gridy = 4;

        mainPanel.add(
                new JLabel("Class Type:"),
                gbc
        );

        classTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "Sleeper",
                                "AC 3 Tier",
                                "AC 2 Tier",
                                "AC First Class",
                                "Chair Car"
                        }
                );

        gbc.gridx = 1;

        mainPanel.add(
                classTypeComboBox,
                gbc
        );

        // Journey Date
        gbc.gridx = 0;
        gbc.gridy = 5;

        mainPanel.add(
                new JLabel("Journey Date (DD-MM-YYYY):"),
                gbc
        );

        journeyDateField =
                new JTextField(20);

        gbc.gridx = 1;

        mainPanel.add(
                journeyDateField,
                gbc
        );

        // Source
        gbc.gridx = 0;
        gbc.gridy = 6;

        mainPanel.add(
                new JLabel("Source Station:"),
                gbc
        );

        sourceField =
                new JTextField(20);

        gbc.gridx = 1;

        mainPanel.add(
                sourceField,
                gbc
        );

        // Destination
        gbc.gridx = 0;
        gbc.gridy = 7;

        mainPanel.add(
                new JLabel("Destination Station:"),
                gbc
        );

        destinationField =
                new JTextField(20);

        gbc.gridx = 1;

        mainPanel.add(
                destinationField,
                gbc
        );

        // Buttons
        bookButton =
                new JButton("Book Ticket");

        cancelButton =
                new JButton("Clear");

        cancellationButton =
                new JButton("Cancel Ticket");

        gbc.gridx = 0;
        gbc.gridy = 8;

        mainPanel.add(
                bookButton,
                gbc
        );

        gbc.gridx = 1;

        mainPanel.add(
                cancelButton,
                gbc
        );

        // Cancellation button
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;

        mainPanel.add(
                cancellationButton,
                gbc
        );

        gbc.gridwidth = 1;

        // Train number listener
        trainNumberField.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateTrainName();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateTrainName();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateTrainName();
                    }
                }
        );

        // Book button
        bookButton.addActionListener(
                e -> bookTicket()
        );

        // Clear button
        cancelButton.addActionListener(
                e -> clearForm()
        );

        // Cancellation button
        cancellationButton.addActionListener(
                e -> new CancellationFrame()
        );

        add(mainPanel);
    }

    private void updateTrainName() {

        String trainNumberText =
                trainNumberField.getText().trim();

        if (trainNumberText.isEmpty()) {

            trainNameField.setText("");
            return;
        }

        try {

            int trainNumber =
                    Integer.parseInt(trainNumberText);

            String trainName =
                    trains.get(trainNumber);

            if (trainName != null) {

                trainNameField.setText(trainName);

            } else {

                trainNameField.setText(
                        "Train not found"
                );
            }

        } catch (NumberFormatException e) {

            trainNameField.setText(
                    "Invalid train number"
            );
        }
    }

    private void bookTicket() {

        String passengerName =
                passengerNameField.getText().trim();

        String trainNumberText =
                trainNumberField.getText().trim();

        String trainName =
                trainNameField.getText().trim();

        String classType =
                (String) classTypeComboBox.getSelectedItem();

        String journeyDate =
                journeyDateField.getText().trim();

        String source =
                sourceField.getText().trim();

        String destination =
                destinationField.getText().trim();

        // Required field validation
        if (passengerName.isEmpty()
                || trainNumberText.isEmpty()
                || journeyDate.isEmpty()
                || source.isEmpty()
                || destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Train number validation
        int trainNumber;

        try {

            trainNumber =
                    Integer.parseInt(trainNumberText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Train number must be numeric.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Train validation
        if (!trains.containsKey(trainNumber)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid train number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        trainName =
                trains.get(trainNumber);

        trainNameField.setText(trainName);

        // Date validation
        if (!isValidDate(journeyDate)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date. Use DD-MM-YYYY.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Reservation reservation =
                new Reservation(
                        passengerName,
                        trainNumber,
                        trainName,
                        classType,
                        journeyDate,
                        source,
                        destination
                );

        int pnr =
                reservationDAO.addReservation(
                        reservation
                );

        if (pnr != -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking successful!\n\n"
                            + "PNR: " + pnr + "\n"
                            + "Passenger: " + passengerName + "\n"
                            + "Train: " + trainName + "\n"
                            + "Class: " + classType + "\n"
                            + "Journey Date: " + journeyDate + "\n"
                            + "From: " + source + "\n"
                            + "To: " + destination,
                    "Booking Confirmation",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking failed. Please try again.",
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean isValidDate(String date) {

        SimpleDateFormat format =
                new SimpleDateFormat("dd-MM-yyyy");

        format.setLenient(false);

        try {

            format.parse(date);
            return true;

        } catch (ParseException e) {

            return false;
        }
    }

    private void clearForm() {

        passengerNameField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        journeyDateField.setText("");
        sourceField.setText("");
        destinationField.setText("");

        classTypeComboBox.setSelectedIndex(0);
    }
}