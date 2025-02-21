package com.devs.mazebank.Views;

import com.devs.mazebank.Controllers.Client.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/// This is a centrall class that controls the UI depending on what actions are taken
public class ViewFactory {
    // Client viewws
    private AnchorPane dashboardView;
    public ViewFactory(){

    }
    public AnchorPane getDashboardView(){
        /*
        The reason we have the if check is so that everytime we want to switch from one view
        to the dashboard, we don't want to have to load it again
         */
        if (dashboardView==null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("./FXML/Client/Dashboard.fxml")).load();
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
        Scene scene = null;
        return scene;
    }


}
