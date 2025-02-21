package com.devs.mazebank;

import com.devs.mazebank.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the entry point of the Application
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
