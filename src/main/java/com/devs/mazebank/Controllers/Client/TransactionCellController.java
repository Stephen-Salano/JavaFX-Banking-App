package com.devs.mazebank.Controllers.Client;

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

    private Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
