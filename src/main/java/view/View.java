package view;

import controllers.BaseController;
import controllers.MainWindowController;
import controllers.Weather;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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
            stage.setWidth(750);
            stage.setHeight(400);
            stage.setTitle("Weahter App");
            stage.setResizable(false);
            stage.show();
            setCssStyle(scene, Style.DARK);

        }catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();

        }catch (Exception e){

        }
    }

    public void showMainWindow(){
        MainWindowController mainWindowController = new MainWindowController(this,"mainWindow.fxml");

        initializageStage(mainWindowController);

    }

    private void setCssStyle(Scene scene, Style style) {
        try {
            scene.getStylesheets().add(getClass().getResource(style.path).toExternalForm());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("jest w css");
        }

    }
}
