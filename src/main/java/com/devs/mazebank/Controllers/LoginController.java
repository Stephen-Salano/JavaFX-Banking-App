package com.devs.mazebank.Controllers;

import com.devs.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox account_selector;
    public Label payee_address_lbl;
    public TextField payee_adress_field;
    public Label password_lbl;
    public PasswordField password_field;
    public Button login_btn;
    public Label error_lbl;

    // When we click the login button it will take us to the client window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(e ->{
            // Get the current stage (login window) and close it
            Stage stage = (Stage) login_btn.getScene().getWindow();
            stage.close();
            // Show the client dashboard
            Model.getInstance().getViewFactory().showClientWindow();
        });
    }
}
