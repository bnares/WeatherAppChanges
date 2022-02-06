package controllers;

import view.View;

public abstract class BaseController {

    private String fxmlFileName;
    private View view;

    public BaseController(View view, String fxmlFile){
        this.fxmlFileName = fxmlFile;
        this.view = view;
    }

    public String getFxmlFile(){
        return this.fxmlFileName;
    }
}
