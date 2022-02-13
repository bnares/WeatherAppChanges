package view;

import controllers.BaseController;
import controllers.MainWindowController;
import controllers.Weather;
import exception.CssException;
import exception.FileCantBeFoundExceptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class View {

    private void initializageStage(BaseController baseController) throws CssException, FileCantBeFoundExceptions {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+baseController.getFxmlFile()));

        fxmlLoader.setController(baseController);
        Parent parent = null;
        try{
            parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(false);
            stage.setWidth(750);
            stage.setHeight(400);
            stage.setTitle("Weahter App");
            stage.setResizable(false);
            stage.show();
            setCssStyle(scene, Style.DARK);

        }catch(IOException ex){
            System.out.println(ex.getMessage());
            throw new FileCantBeFoundExceptions();
        }catch (NullPointerException e){
            throw new CssException();
        }
    }

    public void showMainWindow(){
        MainWindowController mainWindowController = new MainWindowController(this,"mainWindow.fxml");
        try {
            initializageStage(mainWindowController);
        }catch (CssException e){
            mainWindowController.setErrorLabel("cant find graphic file");
        }catch (FileCantBeFoundExceptions ex){
            mainWindowController.setErrorLabel("Cant display Main window. contact with support");
        }
    }

    private void setCssStyle(Scene scene, Style style) throws CssException {
        try {
            scene.getStylesheets().add(getClass().getResource(style.path).toExternalForm());
        }catch (NullPointerException ex){
           throw new CssException();
        }
    }
}
