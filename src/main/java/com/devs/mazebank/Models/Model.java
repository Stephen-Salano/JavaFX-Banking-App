package com.devs.mazebank.Models;

import com.devs.mazebank.Views.AccountType;
import com.devs.mazebank.Views.ClientMenuOptions;
import com.devs.mazebank.Views.ViewFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

///  This class implements the Singleton pattern to ensure that only one instance of Model exists in the
/// entire Application.
public class Model {
    private final ViewFactory viewFactory;
    // A static variable to hold the single instance of Model
    // It starts as `null` and gets initialized only once
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private AccountType loginAccountType = AccountType.CLIENT;
    ///  Client Data Section
    private Client client;
    private boolean isLoggedIn;

    /// Admin Data Section

    // The constructor is private, preventing external classes
    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // Client Section
        this.isLoggedIn = false;
        this.client = new Client("", "", "", null, null, null);
        //Adin Section
    }
    // Ensures only one instance of Model exists
    ///  the `sychronized` keyword ensures thread safety, preventing multiple threads from creating separate
    /// instances at the same time
    public  static synchronized Model getInstance(){
        // will return a single instance of the model class
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /// Client Method Section
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setLoggedIn(boolean flag){
        this.isLoggedIn = flag;
    }

    public Client getClient() {
        return client;
    }

    public void evaluateClientCredentials(String pAddress, String password){
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;

        ResultSet resultSet = databaseDriver.getClientData(pAddress, password);

        if (resultSet == null) {
            System.out.println("Querry execution failed or returned no results");
            // prevent further execution
            return;
        }
        try{
            // Is result set empty?
            if (resultSet.isBeforeFirst()){
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of
                        (Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]),
                        Integer.parseInt(dateParts[2])
                        );
                this.client.dateCreatedProperty().set(date);
                setLoggedIn(true);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void logout() {
            // Reset the client data
            this.client = new Client("", "", "", null, null, null);
            // Set the logged in status to false
            this.isLoggedIn = false;
            // Reset Ui selections
            this.viewFactory.getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
            // Clear client views to ensure fresh data on next login
            this.viewFactory.clearClientViews();
    }


}
