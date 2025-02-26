package com.devs.mazebank.Views;

import com.devs.mazebank.Controllers.Client.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/// This is a central class that controls the UI depending on what actions are taken
public class ViewFactory {
    // Client views
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane accountsView;

    public ViewFactory(){

        clientSelectedMenuItem = new SimpleStringProperty("");
    }
    public AnchorPane getDashboardView(){
        /*
        The reason we have the if check is so that everytime we want to switch from one view
        to the dashboard, we don't want to have to load it again
         */
        if (dashboardView==null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/FXML/Client/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = createStage();
        try{
            scene = new  Scene(loader.  load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze bank");
        stage.show();
    }

    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        // Adding the client controller  manually
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        Scene scene = createStage();
        try{
            scene = new  Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze bank");
        stage.show();
    }

    private static Scene createStage() {
        return null;
    }

    /// This method is a lazy-loaded singleton getter for the AnchorPane representing the transactions view
    /// If the view was successfully loaded, it returns the AnchorPane
    /// If it was already loaded previously, it returns the existing instance
    public AnchorPane getTransactionsView() {
        /*
          Checks if the transaction view is already initialized
          This ensures that the view is loaded only once, if it is loaded, it simply returns the existing
          instance
        */

        if (transactionsView == null) {
            try{
                /*
                    Load the FXML File:
                    We use the FXMLLoader to load the Transaction.fxml file from the FXML/Client directory
                    The `.load` method parses the FXML file and constructs te UI components, returning an Anchorpane
                 */
                transactionsView = new FXMLLoader(getClass().getResource("/FXML/Client/Transactions.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        // return the loaded view
        return transactionsView;
    }

    public AnchorPane getAccountsView() {
        if (accountsView == null){
            try{
                accountsView = new FXMLLoader(getClass().getResource("/FXML/Client/Accounts.fxml")).load();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    /// Clients Views Section
    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public StringProperty clientSelectedMenuItemProperty() {
        return clientSelectedMenuItem;
    }
    // TODO If client window is closed bring pop up asking if we're sure we want to close it


}
