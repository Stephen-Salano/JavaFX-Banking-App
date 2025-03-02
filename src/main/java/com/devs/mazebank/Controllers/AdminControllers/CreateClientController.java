package com.devs.mazebank.Controllers.AdminControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fname_fld;
    public TextField lname_fld;
    public TextField password_fld;
    public CheckBox pAdress_box;
    public Label pAddress_lbl;
    public CheckBox checkings_acc_box;
    public TextField checkings_amount_fld;
    public CheckBox savings_acc_box;
    public TextField savings_amount_fld;
    public Button create_client_button;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
