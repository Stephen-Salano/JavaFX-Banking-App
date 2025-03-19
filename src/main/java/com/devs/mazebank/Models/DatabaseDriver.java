package com.devs.mazebank.Models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
//TODO: Add custom exception handling
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Testing Constructor
    public DatabaseDriver(Connection conn) {
        this.conn = conn;
    }

    /// Client Section
    /// 1. Get Client Data to allow login
    public ResultSet getClientData(String pAddress, String password) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM Clients WHERE PayeeAddress = ? AND Password = ?";

        try{
            if (conn == null || conn.isClosed()) {
                System.out.println("Database connection is null or closed");
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, pAddress);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    ///  Getting Transaction Data to allow for display of Transactions in ListView
    /// TODO: Check if Logged
    public ObservableList<Transaction> getTransactionsForClients(String clientPayeeAddress){
        ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
        ResultSet resultSet;
        String sql = "SELECT * FROM Transactions WHERE Sender = ? OR Receiver = ?";
       try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)){
           preparedStatement.setString(1, clientPayeeAddress);
           preparedStatement.setString(2, clientPayeeAddress);
           resultSet = preparedStatement.executeQuery();
           while (resultSet.next()){
               String sender = resultSet.getString("Sender");
               String reciever = resultSet.getString("Receiver");
               double amount = resultSet.getDouble("Amount");
               LocalDate date = LocalDate.parse(resultSet.getString("Date")); // Becuase dates stored as TEST
               String message = resultSet.getString("Message");

               // Creating a new Transaction Object
               Transaction transaction = new Transaction(sender, reciever, amount, date, message );
               transactionsList.add(transaction);
           }

       } catch (SQLException e){
           e.printStackTrace();
       }
       return transactionsList;
    }



    ///  Admin Section


    ///  Utility Methods (can be used by both admin and clients)
}
