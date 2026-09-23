package com.reservation.dao;

import com.reservation.database.DatabaseConnection;
import com.reservation.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDAO {

    // Save a new reservation and return the generated PNR
    public int addReservation(Reservation reservation) {

        String sql = """
                INSERT INTO reservations
                (passenger_name, train_number, train_name, class_type,
                 journey_date, source, destination)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, reservation.getPassengerName());
            statement.setInt(2, reservation.getTrainNumber());
            statement.setString(3, reservation.getTrainName());
            statement.setString(4, reservation.getClassType());
            statement.setString(5, reservation.getJourneyDate());
            statement.setString(6, reservation.getSource());
            statement.setString(7, reservation.getDestination());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    int pnr = keys.getInt(1);
                    reservation.setPnr(pnr);
                    return pnr;
                }
            }

        } catch (SQLException e) {
            System.out.println("Booking failed.");
            e.printStackTrace();
        }

        return -1;
    }

    // Find a reservation using its PNR
    public Reservation getReservationByPnr(int pnr) {

        String sql = """
                SELECT *
                FROM reservations
                WHERE pnr = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, pnr);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Reservation reservation = new Reservation();

                    reservation.setPnr(
                            resultSet.getInt("pnr"));

                    reservation.setPassengerName(
                            resultSet.getString("passenger_name"));

                    reservation.setTrainNumber(
                            resultSet.getInt("train_number"));

                    reservation.setTrainName(
                            resultSet.getString("train_name"));

                    reservation.setClassType(
                            resultSet.getString("class_type"));

                    reservation.setJourneyDate(
                            resultSet.getString("journey_date"));

                    reservation.setSource(
                            resultSet.getString("source"));

                    reservation.setDestination(
                            resultSet.getString("destination"));

                    return reservation;
                }
            }

        } catch (SQLException e) {
            System.out.println("Could not fetch reservation.");
            e.printStackTrace();
        }

        return null;
    }

    // Delete a reservation using its PNR
    public boolean cancelReservation(int pnr) {

        String sql = """
                DELETE FROM reservations
                WHERE pnr = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, pnr);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Cancellation failed.");
            e.printStackTrace();
            return false;
        }
    }
}