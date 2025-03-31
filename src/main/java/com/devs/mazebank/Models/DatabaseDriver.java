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
        String sql = "SELECT * FROM Transactions WHERE Sender = ? OR Receiver = ? ORDER BY Date DESC";
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

    // Get Checking account info to bind to Account section
    public ObservableList<CheckingAccount> getCheckingAccountInfo (String payeeAddress) {
        ObservableList<CheckingAccount> checkingAccountsInfoList = FXCollections.observableArrayList();
        ResultSet resultSet;
        String query = "SELECT * FROM CheckingAccounts WHERE Owner = ?";
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String owner = resultSet.getString("Owner");
                String accountNumber =  resultSet.getString("AccountNumber");
                int transactionLimits = (int) resultSet.getDouble("TransactionLimit");
                double balance = resultSet.getDouble("Balance");

                CheckingAccount checkingAccount = new CheckingAccount(
                    owner, accountNumber, balance, transactionLimits
                );
                checkingAccountsInfoList.add(checkingAccount);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
         return checkingAccountsInfoList;
    }

    // Get Savings account info to bind to Account section
    public ObservableList<SavingsAccount> getSavingsAccountInfo (String payeeAddress) {
        ObservableList<SavingsAccount> savingsAccountList = FXCollections.observableArrayList();
        ResultSet resultSet;
        String query = "SELECT * FROM SavingsAccounts WHERE Owner = ?";
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String owner = resultSet.getString("Owner");
                String accountNumber =  resultSet.getString("AccountNumber");
                int transactionLimits  = resultSet.getInt("WithdrawalLimit");
                double balance = resultSet.getDouble("Balance");

                SavingsAccount savingsAccount = new SavingsAccount(
                        owner, accountNumber, balance, transactionLimits
                );
                savingsAccountList.add(savingsAccount);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return savingsAccountList;
    }

    public boolean createTransaction(String sender, String reciever, double amount, String message) {
        // check if sender has sufficient funds
        if (!hasSufficientFunds(sender, amount)){
            return false;
        }

        // Begin transaction
        try{
            // Set auto-commit to false to handle transaction manually
            conn.setAutoCommit(false);

            // 1. Create a new transaction record
            String createTransactionSQL = "INSERT INTO Transactions(Sender, Receiver, Amount, Date, Message) " +
                    "VALUES(?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = conn.prepareStatement(createTransactionSQL)){
                preparedStatement.setString(1, sender);
                preparedStatement.setString(2, reciever);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, LocalDate.now().toString());
                preparedStatement.setString(5, message);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected !=1){
                    conn.rollback();
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // 2. Update senders balance (deduct amount)
            // This prevents a scenario where money is deducted but not recieved
            if (!updateBalance(sender, -amount) || !updateBalance(reciever, amount)){
                conn.rollback();
                return false;
            }
            // 3. Update reciever's balance (add amount)
            if (!updateBalance(reciever, amount)){
                conn.rollback();
                return false;
            }

            // Commit transaction if all operations successful
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e){
            try{
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    // In DatabaseDriver.java
    public boolean transferBetweenAccounts(String owner, double amount, String fromAccountType) {
        // First we validate if the transfer is possible
        if (!validateTransfer(owner, amount, fromAccountType)) {
            System.out.println("Transfer validation failed");
            return false;
        }
        try {
            conn.setAutoCommit(false);

            // Determine account types
            String toAccountType = (fromAccountType.equals("CheckingAccounts")) ? "SavingsAccounts" : "CheckingAccounts";

            // 1. Create transaction record
            String createTransactionQuery = "INSERT INTO Transactions(" +
                    "Sender, Receiver, Amount, Date, Message) " +
                    "VALUES(?, ?, ?, ?, ?)";

            try(PreparedStatement preparedStatement = this.conn.prepareStatement(createTransactionQuery)) {
                preparedStatement.setString(1, owner);  // Same owner
                preparedStatement.setString(2, owner);  // Same owner
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, LocalDate.now().toString());
                preparedStatement.setString(5, "Inter-account transfer");

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected != 1) {
                    conn.rollback();
                    return false;
                }
            }

            // 2. Deduct from source account
            if (!updateAccountBalance(owner, -amount, fromAccountType)) {
                conn.rollback();
                return false;
            }

            // 3. Add to destination account
            if (!updateAccountBalance(owner, amount, toAccountType)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateAccountBalance(String payeeAddress, double amount, String accountType) {
        String query = (accountType.equals("CheckingAccounts"))
                ? "UPDATE CheckingAccounts SET Balance = Balance + ? WHERE Owner = ?"
                : "UPDATE SavingsAccounts SET Balance = Balance + ? WHERE Owner = ?";

        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, payeeAddress);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateTransfer(String payeeAddress, double amount, String accountType) {

        System.out.println("Validating transfer from " + accountType + " for " + payeeAddress + ", amount: " + amount);
        // check account specific limits
        if (accountType.equals("CheckingAccounts")){
            // Check transaction limit logic
            if (hasDailyTransactionLimitExceeded(payeeAddress)){
                return false;
            }
        } else if (accountType.equals("SavingsAccounts")) {
            // Check withdrawal limit logic
            if (hasExceededWithdrawalLimit(payeeAddress, amount)){
                return false;
            }
        }
        return hasSufficientFunds(payeeAddress, amount, accountType);
    }

    private boolean hasExceededWithdrawalLimit(String payeeAddress, double amount) {
        return false;
    }

    private boolean hasDailyTransactionLimitExceeded(String payeeAddress) {
            return false;
    }
    ///  After a transaction event has taken place we update the Balance
    private boolean updateBalance(String payeeAddress, double amount) {
        String query = "UPDATE CheckingAccounts SET Balance = Balance + ? WHERE Owner = ?";
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, payeeAddress);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected ==1;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    ///  Checking if the sender has enough funds to send money
    private boolean hasSufficientFunds(String payeeAddress, double amount, String accountType) {
        String query = (accountType.equals("CheckingAccounts"))
                ? "SELECT Balance FROM CheckingAccounts WHERE Owner = ?"
                : "SELECT Balance FROM SavingsAccounts WHERE Owner = ?";

        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                double balance = resultSet.getDouble("Balance");
                return balance >= amount;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean hasSufficientFunds(String payeeAddress, double amount) {
        // This should check just the checking account balance by default
        return hasSufficientFunds(payeeAddress, amount, "CheckingAccounts");
    }

    /// Get checking account balance for a client
    public double getCheckingAccountBalance(String payeeAddress){
        String query = "SELECT Balance FROM CheckingAccounts WHERE Owner = ?";
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return resultSet.getDouble("Balance");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0.0;
    }

    //Get checking account number for a client
    public String getCheckingAccountNumber(String payeeAddress){
        String query = "SELECT AccountNumber FROM CheckingAccounts WHERE Owner = ?";
        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("AccountNumber");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //Get Savings Account Balance for a client
    public double getSavingsAccountBalance(String payeeAddress){
        // create the query to query database
        String query = "SELECT Balance FROM SavingsAccounts WHERE Owner = ?";

        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("Balance");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public String getSavingsAccountNumber(String payeeAddress){
        String query = "SELECT AccountNumber FROM SavingsAccounts WHERE Owner = ?";

        try(PreparedStatement preparedStatement = this.conn.prepareStatement(query)){
            preparedStatement.setString(1, payeeAddress);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("AccountNumber");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    ///  Admin Section


    ///  Utility Methods (can be used by both admin and clients)
}
