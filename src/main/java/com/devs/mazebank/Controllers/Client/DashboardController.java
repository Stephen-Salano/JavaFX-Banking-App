package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.Model;
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
    }

    private void bindUserData(){
        // Set the user's name in the Dashboard
        user_name.textProperty().bind(
                model.getClient().firstNameProperty().concat(" ").concat(model.getClient().lastNameProperty())
        );

        // Set the current date
        login_date.setText("Today, " + LocalDate.now());

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

}
