package com.devs.mazebank.Models;

import java.sql.*;

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

    /// Client Section
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



    ///  Admin Section


    ///  Utility Methods (can be used by both admin and clients)
}
