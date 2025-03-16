package com.devs.mazebank;

import com.devs.mazebank.Models.DatabaseDriver;
import com.devs.mazebank.Models.Transaction;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseDriverTest {

    private static DatabaseDriver databaseDriver;
    private static Connection connection;

    @BeforeAll
    static void setup() {
        try {
            // Connect to the existing database
            connection = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
            databaseDriver = new DatabaseDriver(connection);
            System.out.println("Connected to existing mazebank.db");
        } catch (SQLException e) {
            Assertions.fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    void testGetTransactionsForClients() {
        String testClient = "@bBaker1"; // Use an actual email from your database

        ObservableList<Transaction> transactions = databaseDriver.getTransactionsForClients(testClient);

        // Print retrieved transactions
        System.out.println("Transactions for " + testClient + ":");
        for (Transaction t : transactions) {
            System.out.println("Sender: " + t.senderAddressProperty().get() +
                    ", Receiver: " + t.receiverAddressProperty().get() +
                    ", Amount: " + t.amountToBeSentProperty().get() +
                    ", Date: " + t.dateProperty().get() +
                    ", Message: " + t.messageProperty().get());
        }

        Assertions.assertNotNull(transactions, "Transactions list should not be null");
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
        System.out.println("Database connection closed.");
    }
}
