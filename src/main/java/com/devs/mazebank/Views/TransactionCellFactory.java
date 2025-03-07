package com.devs.mazebank.Views;

import com.devs.mazebank.Controllers.Client.TransactionCellController;
import com.devs.mazebank.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

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
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/TransactionCell.fxml")).load();
                TransactionCellController controller = new TransactionCellController(transaction);
                loader.setController(controller);
                setText(null);
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
