package com.devs.mazebank.Views;

import com.devs.mazebank.Controllers.AdminControllers.ClientCellController;
import com.devs.mazebank.Models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;
/// This class extends ListCell<Client> to customize how each Client appears in the ListView.
///
/// - If the cell is empty, it clears the text and graphic.
/// - If the cell contains a Client, it:
///   - Loads ClientCell.fxml to define the UI layout.
///   - Creates a ClientCellController and passes the Client object to it.
///   - Sets the UI for this client inside the list.


public class ClientCellFactory extends ListCell<Client> {

    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if (empty){
            setText(null);
            setGraphic(null);

        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ClientCell.fxml")).load();
                ClientCellController controller = new ClientCellController(client);
                loader.setController(controller);
                setText(null);
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
