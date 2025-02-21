package com.devs.mazebank.Models;

import com.devs.mazebank.Views.ViewFactory;

public class Model {
    private final ViewFactory viewFactory;
    private static Model model;

    private Model(){
        this.viewFactory = new ViewFactory();

    }
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
