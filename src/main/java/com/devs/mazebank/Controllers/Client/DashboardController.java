package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Models.Transaction;
import com.devs.mazebank.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_balance;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    private Model model;

    /// Code that will run when we call it's fxml to be viewed
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = Model.getInstance();

        // initialize user info
        bindUserData();

        // Initialize transaction List
        initializeTransactionsList();

        //Set up the UI interactions
        initializeUI();
    }

    private void initializeUI() {
        // Connect the send money button to the send money method
        send_money_btn.setOnAction(e -> sendMoney());

        // Add a listener to format the amount field to only accept numbers
        amount_fld.textProperty().addListener(((observable, oldVal, newVal) ->{
            // using Regex expressions
            if (!newVal.matches("\\d*(\\.\\d*)?")){
                amount_fld.setText(oldVal);
            }
        } ));

    }

    private void bindUserData(){
        // Set the user's name in the Dashboard
        user_name.textProperty().bind(
                model.getClient().firstNameProperty().concat(" ").concat(model.getClient().lastNameProperty())
        );

        // Set the current date
        login_date.setText("Today, " + LocalDate.now());

        // Get and set checking account info
        String clientPayeeAddress = model.getClient().payeeAddressProperty().get();
        double checkingBalance = model.getDatabaseDriver().getCheckingAccountBalance(clientPayeeAddress);
        String checkingAccountNumber = model.getDatabaseDriver().getCheckingAccountNumber(clientPayeeAddress);
        checking_balance.setText("$" + String.format("%.2f", checkingBalance));
        checking_acc_num.setText(checkingAccountNumber);

        // Get and Set Savings Account info
        double savingsBalance = model.getDatabaseDriver().getSavingsAccountBalance(clientPayeeAddress);
        String savingsAccountNumber = model.getDatabaseDriver().getSavingsAccountNumber(clientPayeeAddress);
        savings_bal.setText("$" + String.format("%.2f", savingsBalance));
        savings_acc_num.setText(savingsAccountNumber);

        /*
        TODO: When implementing the Transfer from Savings to checking and vice versa remember to reload the
        savings account Balance

         */


    }
    private void initializeTransactionsList(){
        //Set the custom cell factory for transactions
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());

        //Get the clients payee address from the model
        String clientPayeeAdress = model.getClient().payeeAddressProperty().get();

        // Fetch Transactions for this client from the database
        transaction_listview.setItems(
                model.getDatabaseDriver().getTransactionsForClients(clientPayeeAdress)
        );
    }

    private void sendMoney(){
        // Get the client's payee Address (sender)
        String sender = model.getClient().payeeAddressProperty().get();
        // Get the reciever adress (reciever)
        String reciever = payee_fld.getText();
        // Get amount to be sent
        double amount;
        try{
            amount = Double.parseDouble(amount_fld.getText());
        } catch (NumberFormatException e){
            // Show error message if amount is not a valid number
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount!");
            return;
        }

        // Get the message
        String message = message_fld.getText();

        // Validate input
        if (reciever.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a receiver's valid address!");
            return;
        }
        if (amount <= 0){
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter an amount greater than 0!");
        }

        // Create transaction
        boolean result = model.getDatabaseDriver().createTransaction(sender, reciever, amount, message);

        if(result){
            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction Completed successfully!");

            // Clear the input fields
            payee_fld.clear();
            amount_fld.clear();
            message_fld.clear();

            //Refresh account information
            updateAccountInfo();

            // Refresh the Transactions list
            initializeTransactionsList();

        } else{
            showAlert(Alert.AlertType.ERROR, "Error", "Transaction failed! Please check your balance or try again later");
        }
    }

    ///  This method will update the account information from the client dashboard after transactions
    private void updateAccountInfo() {
        String clientPayeeAddress = model.getClient().payeeAddressProperty().get();

        //Update checking account balance
        double checkingBalance = model.getDatabaseDriver().getCheckingAccountBalance(clientPayeeAddress);
        checking_balance.setText("$" + String.format("%.2f", checkingBalance));

        // TODO: Implement savings account display
        // double savingsBalance = model.getDatabaseDriver().getSavingsAccountBalance(clientPayeeAddress);
        // savings_bal.setText("$" + String.format("%.2f", savingsBalance));
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
