package com.devs.mazebank.Controllers.Client;

import com.devs.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is where we'll b able to change the center property of the BorderPane
 */
public class ClientController implements Initializable {

    public BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().
                addListener((observableValue, oldVal, newVal) ->{
                    switch (newVal){
                        case "Transactions" -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                        default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                    }
                } );
    }
}
