package com.devs.mazebank.Controllers.AdminControllers;

import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    // TODO: Add a home controller for the admin dashboard

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        ///  On button press of createclient button it will open the correct view based on the listener attached
        create_client_btn.setOnAction(e -> onCreateClient());
        ///  On button press of clients button it will open the clients section
        clients_btn.setOnAction(e -> onClients());
        ///  On Deposit button click
        deposit_btn.setOnAction(e -> onDeposit());

    }
    private void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT_ACCOUNT);
    }
    private void onClients(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
    }

    private void onDeposit(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DEPOSIT);
    }
}
