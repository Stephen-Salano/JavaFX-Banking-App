package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
///  Controller for the UI for the Transaction cells
///
/// - Manages the FXML files components labels and buttons
/// - Receives a Transaction object in its constructor
/// - Will be used to fill labels with the transaction details
public class TransactionCellController implements Initializable {
    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the clients payee address to determine transaction direction
        String clientAddress = Model.getInstance().getClient().payeeAddressProperty().get();

        // get the transaction date
        trans_date_lbl.textProperty().bind(transaction.dateProperty().asString());

        //Set Sender and reciever label
        sender_lbl.textProperty().bind(transaction.senderAddressProperty());
        receiver_lbl.textProperty().bind(transaction.receiverAddressProperty());

        //Set the amount with dolar sign
        amount_lbl.textProperty().bind(transaction.amountToBeSentProperty().asString());

        //Show the appropriate arow icon based on whether money is coming in or going out
        if (transaction.senderAddressProperty().get().equals(clientAddress)){
            // TODO: both icons should be visible but incoming transaction icon shoul be green and vice versa

            in_icon.setVisible(false);
            out_icon.setVisible(true);

        } else {
            //This is an incoming Transaction
            in_icon.setVisible(true);
            out_icon.setVisible(false);
        }

    }
}
