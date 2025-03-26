package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.CheckingAccount;
import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Models.SavingsAccount;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label check_acc_num;
    public Label transaction_limit;
    public Label check_acc_date;
    public Label check_acc_bal;
    public Label saving_acc_num;
    public Label savings_withdraw_lim;
    public Label savings_acc_date;
    public Label savings_acc_bal;
    public TextField amount_to_savings;
    public Button transfer_to_savings_btn;
    public TextField ammount_to_checkings;
    public Button transfer_to_checkings_btn;

    private Model model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = Model.getInstance();

        loadCheckingAccountDetails();
        loadSavingsAccountDetails();
    }

    public void loadCheckingAccountDetails(){
        // get client Payee address
        String clientPayeeAddress = model.getClient().payeeAddressProperty().get();

        // get the client checking account details
        ObservableList<CheckingAccount> clientAccountDetails = model.getDatabaseDriver().getCheckingAccountInfo(clientPayeeAddress);
        // Setting checking Account Details details from the database
        for(CheckingAccount checkingAccount: clientAccountDetails){
            check_acc_num.setText(checkingAccount.accountNumberProperty().get());
            transaction_limit.setText(String.valueOf(checkingAccount.transactionLimitProperty().get()));
            check_acc_date.setText(String.valueOf(model.getClient().dateCreatedProperty().get()));
            check_acc_bal.setText(String.valueOf(checkingAccount.balanceProperty().get()));
        }

    }
    public void loadSavingsAccountDetails(){

        // get the client payee address
        String clientPayeeAddress = model.getClient().payeeAddressProperty().get();

        // get the client savingsAccount details
        ObservableList<SavingsAccount> savingsAccountObservableList = model.getDatabaseDriver().getSavingsAccountInfo(clientPayeeAddress);

        // Setting the Savings Account details from the database
        for (SavingsAccount savingsAccount: savingsAccountObservableList){
            saving_acc_num.setText(savingsAccount.accountNumberProperty().get());
            savings_withdraw_lim.setText(String.valueOf(savingsAccount.withdrawalLimitProperty().get()));
            savings_acc_date.setText(String.valueOf(model.getClient().dateCreatedProperty().get()));
            savings_acc_bal.setText(String.valueOf(savingsAccount.balanceProperty().get()));
        }
    }
}
