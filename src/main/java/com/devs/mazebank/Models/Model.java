package com.devs.mazebank.Models;

import com.devs.mazebank.Views.ViewFactory;

///  This class implements the Singleton pattern to ensure that only one instance of Model exists in the
/// Application.
public class Model {
    private final ViewFactory viewFactory;
    // A static variable to hold the single instance of Model
    // It starts as `null` and gets initialized only once
    private static Model model;

    // The constructor is private, preventing external classes
    private Model(){
        this.viewFactory = new ViewFactory();

    }
    // Ensures only one instance of Model exists
    ///  the `sychronized` keyword ensures thread safety, preventing multiple threads from creating separate
    /// instances at the same time
    public  static synchronized Model getInstance(){
        // will return a single instance of the model class
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
