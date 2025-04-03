package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    /*
    This changes the views of the center area on the dashboard
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }

    private void addListener(){
        dashboard_btn.setOnAction(e -> onDashboard());
        transaction_btn.setOnAction(e -> onTransaction());
        accounts_btn.setOnAction(e -> onAccounts());
        logout_btn.setOnAction(e -> onLogout());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransaction() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }
    // Logout button
    private void onLogout(){
        // Get the current stage
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        // Show the Alert box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Anu unsaved changes will be lost");

        // Making the alert a modal dialog for the current stage
        alert.initOwner(stage);

        // Setting buttons
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        // Show dialogue box and wait for the user response
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        // If the user clicks OK, exit the application
        if (result == ButtonType.OK) {
            // Call logout method from model
            Model.getInstance().logout();
            // close the current window
            Model.getInstance().getViewFactory().closeStage(stage);
            // Show login window
            Model.getInstance().getViewFactory().showLoginWindow();
        }
    }
}
