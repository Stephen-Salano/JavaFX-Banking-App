package com.devs.mazebank.Controllers.AdminControllers;

import com.devs.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem()
                .addListener((observableValue, oldVal, newVal) ->{
                    //TODO: Add switch statement to change views
                    switch (newVal){
                        case CLIENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientView());
                        case DEPOSIT -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                        default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
                    }
                } );
    }
}
