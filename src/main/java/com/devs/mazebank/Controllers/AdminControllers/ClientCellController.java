package com.devs.mazebank.Controllers.AdminControllers;

import com.devs.mazebank.Models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
/// Controller for the UI:
/// - Manages the FXML files components labels and buttons
/// - Receives a Client object in its constructor
/// - Will be used to fill labels with the clients details
public class ClientCellController implements Initializable {

    public Label fname_lbl;
    public Label lname_lbl;
    public Label pAdress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;

    private Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

