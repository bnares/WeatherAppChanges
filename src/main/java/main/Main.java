package main;

import controllers.MainWindowController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){

        View view = new View();
        view.showMainWindow();
    }
}
