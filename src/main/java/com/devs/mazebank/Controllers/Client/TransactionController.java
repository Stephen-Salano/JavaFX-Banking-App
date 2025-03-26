package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    public ListView transactions_listview;
    ///  instantiating the singleton class to fetch the initializeTransactions method
    private Model model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the singleton instance of model
        this.model = Model.getInstance();
        // Initialize the TransactionList
        initializeTransactionsList();

    }
    private void initializeTransactionsList(){
        //Set the custom cell factory for transactions
        transactions_listview.setCellFactory(e -> new TransactionCellFactory());

        // Get the clients payeeAddress from the model
        String clientPayeeAddress = model.getClient().payeeAddressProperty().get();

        // Fetch Transactions for this client from the database
        transactions_listview.setItems(
                model.getDatabaseDriver().getTransactionsForClients(clientPayeeAddress)
        );
    }
}
