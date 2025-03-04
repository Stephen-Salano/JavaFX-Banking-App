package com.devs.mazebank.Controllers.AdminControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField payeeAddress_fld;
    public Button search_btn;
    public ListView result_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
