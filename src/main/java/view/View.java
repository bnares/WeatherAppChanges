package view;

import controllers.BaseController;
import controllers.MainWindowController;
import controllers.Weather;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class View {

    private void initializageStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+baseController.getFxmlFile()));

        fxmlLoader.setController(baseController);
        Parent parent = null;
        try{
            parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(false);
            stage.setWidth(800);
            stage.setHeight(400);
            stage.setTitle("Weahter App");
            stage.setResizable(false);
            stage.show();

        }catch(IOException ex){
            System.out.println(ex.getMessage());

        }catch (Exception e){

        }
    }

    public void showMainWindow(){
        MainWindowController mainWindowController = new MainWindowController(this,"mainWindow.fxml");
        initializageStage(mainWindowController);
    }


}
