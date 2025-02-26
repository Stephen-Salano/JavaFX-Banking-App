package com.devs.mazebank.Controllers.Client;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
