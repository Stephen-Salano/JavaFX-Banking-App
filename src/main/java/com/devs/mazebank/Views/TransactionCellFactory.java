package com.devs.mazebank.Views;

import com.devs.mazebank.Controllers.Client.TransactionCellController;
import com.devs.mazebank.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/// This class extends ListCell<Transaction> to customize how each Transaction appears in the ListView.
///
/// - If the cell is empty, it clears the text and graphic.
/// - If the cell contains a Transaction, it:
///   - Loads TransactionCell.fxml to define the UI layout.
///   - Creates a TransactionCellController and passes the Transaction object to it.
///   - Sets the UI for this transaction inside the list.
public class TransactionCellFactory extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty || transaction == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                // Create the loader
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/TransactionCell.fxml"));
                // Create the controller with the transaction data
                TransactionCellController controller = new TransactionCellController(transaction);
                // Set the Controller
                loader.setController(controller);
                // Load the FXML
                AnchorPane cellPane = loader.load();
                // Set the Graphic
                setText(null);
                setGraphic(cellPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
