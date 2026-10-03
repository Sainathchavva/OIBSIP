package com.reservation.ui;

import com.reservation.dao.ReservationDAO;
import com.reservation.model.Reservation;

import javax.swing.*;
import java.awt.*;

public class CancellationFrame extends JFrame {

    private JTextField pnrField;

    private JTextArea reservationDetails;

    private JButton fetchButton;
    private JButton cancelButton;
    private JButton backButton;

    private final ReservationDAO reservationDAO;

    private Reservation currentReservation;

    public CancellationFrame() {

        reservationDAO = new ReservationDAO();

        setTitle("Online Reservation System - Cancellation");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JLabel titleLabel =
                new JLabel("CANCEL RESERVATION");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // PNR Panel
        JPanel pnrPanel =
                new JPanel(new FlowLayout());

        pnrPanel.add(
                new JLabel("Enter PNR:")
        );

        pnrField =
                new JTextField(12);

        pnrPanel.add(pnrField);

        fetchButton =
                new JButton("Fetch");

        pnrPanel.add(fetchButton);

        mainPanel.add(
                pnrPanel,
                BorderLayout.CENTER
        );

        // Reservation details
        reservationDetails =
                new JTextArea(12, 40);

        reservationDetails.setEditable(false);

        reservationDetails.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        reservationDetails.setBorder(
                BorderFactory.createTitledBorder(
                        "Reservation Details"
                )
        );

        mainPanel.add(
                new JScrollPane(reservationDetails),
                BorderLayout.SOUTH
        );

        // Buttons
        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        cancelButton =
                new JButton("Cancel Reservation");

        backButton =
                new JButton("Back");

        cancelButton.setEnabled(false);

        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.PAGE_END
        );

        fetchButton.addActionListener(
                e -> fetchReservation()
        );

        cancelButton.addActionListener(
                e -> cancelReservation()
        );

        backButton.addActionListener(
                e -> dispose()
        );

        add(mainPanel);
    }

    private void fetchReservation() {

        String pnrText =
                pnrField.getText().trim();

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a PNR.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int pnr;

        try {

            pnr = Integer.parseInt(pnrText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "PNR must be numeric.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        currentReservation =
                reservationDAO.getReservationByPnr(pnr);

        if (currentReservation == null) {

            reservationDetails.setText("");

            cancelButton.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "No reservation found for PNR: " + pnr,
                    "Reservation Not Found",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        displayReservation(currentReservation);

        cancelButton.setEnabled(true);
    }

    private void displayReservation(
            Reservation reservation) {

        String details =
                "PNR: "
                        + reservation.getPnr()
                        + "\n\n"

                        + "Passenger Name: "
                        + reservation.getPassengerName()
                        + "\n\n"

                        + "Train Number: "
                        + reservation.getTrainNumber()
                        + "\n\n"

                        + "Train Name: "
                        + reservation.getTrainName()
                        + "\n\n"

                        + "Class Type: "
                        + reservation.getClassType()
                        + "\n\n"

                        + "Journey Date: "
                        + reservation.getJourneyDate()
                        + "\n\n"

                        + "Source: "
                        + reservation.getSource()
                        + "\n\n"

                        + "Destination: "
                        + reservation.getDestination();

        reservationDetails.setText(details);
    }

    private void cancelReservation() {

        if (currentReservation == null) {

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this reservation?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        boolean cancelled =
                reservationDAO.cancelReservation(
                        currentReservation.getPnr()
                );

        if (cancelled) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation cancelled successfully!",
                    "Cancellation Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            pnrField.setText("");
            reservationDetails.setText("");

            cancelButton.setEnabled(false);

            currentReservation = null;

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Cancellation failed. Please try again.",
                    "Cancellation Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}