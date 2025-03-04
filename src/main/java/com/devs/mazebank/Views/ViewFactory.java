package com.devs.mazebank.Views;

import com.devs.mazebank.Controllers.AdminControllers.AdminController;
import com.devs.mazebank.Controllers.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/// This is a central class that controls the UI depending on what actions are taken
public class ViewFactory {
    private AccountType loginAccountType;
    // Client views
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane accountsView;

    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;


    public ViewFactory(){
        ///  Default is client account
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    ///  Login Account types
    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    ///  Client Sections :

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

    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        // Adding the client controller  manually
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);

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

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public ObjectProperty<ClientMenuOptions> clientSelectedMenuItemProperty() {
        return clientSelectedMenuItem;
    }

    // TODO If client window is closed bring pop up asking if we're sure we want to close it


    ///  Admin Sections
    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/FXML/Admin/CreateClient.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public void showAdminWindow(){
        ///  Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        ///  Create new controller
        AdminController adminController = new AdminController();
        ///  Set controller manually to it's FXML counterpart
        loader.setController(adminController);
        ///  Call the method to load that FXML to a scene and display it
        createStage(loader);
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public ObjectProperty<AdminMenuOptions> adminSelectedMenuItemProperty() {
        return adminSelectedMenuItem;
    }

    ///  First Window displayed
    public void showLoginWindow(){
        /// loading the FXMl resource
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
        ///  calling the method that will set our FXML resource to a scene and stage and display it
        createStage(loginLoader);
    }

    ///  Loading Window
    ///  This method will take and fxml loaded scene and set it to a new scene and create a new
    ///  stage and display the window
    private static Scene createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new  Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Maze bank");
        stage.show();
        return scene;
    }


    public AnchorPane getClientView() {
        if (clientsView == null){
            try{
                clientsView = new FXMLLoader(getClass().getResource("/FXML/Admin/Clients.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return clientsView;
    }
}
