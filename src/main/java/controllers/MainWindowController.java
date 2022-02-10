package controllers;

import exception.ApiException;
import exception.CreatingObjectExcption;
import exception.FileConvertingExceptions;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.GetWheatherData;
import view.Style;
import view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainWindowController extends BaseController{

    private final String dayHour="12:00:00";
    private final String nightHour = "21:00:00";

    @FXML
    private Label errorLabel;

    @FXML
    private TextField cityNameTextField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label cityNameLabel;

    public MainWindowController(View view, String fxmlFile) {
        super(view,fxmlFile);
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    @FXML
    void findCityButton(){
        errorLabel.setText("");
        if (cityNameTextField.getText().length()<=0 || cityNameTextField.getText().isEmpty()){
            errorLabel.setText("Fill in city name box");
        }else {

            try {
                GetWheatherData weatherData = new GetWheatherData(cityNameTextField.getText());
                String data = weatherData.getFourDaysWheatherData();
                ProcessWeatherData processWeatherData = new ProcessWeatherData(weatherData.collectWeatherDataFourDaysInCollectionForm(data));
                List<Weather> objectWeatherData = processWeatherData.getWeatherObject();
                fillInWeatherMainWIndowWithData(objectWeatherData);
            }catch (ApiException | IOException ex){
                errorLabel.setText("Can not find such city like: "+cityNameTextField.getText());
            }catch (FileConvertingExceptions e){
                errorLabel.setText("Cant download data. Wrong key value");
            }catch (CreatingObjectExcption e){
                errorLabel.setText("Internal error. Please contact with Help department");
            }
        }
    }

    private List<VBox> prepareFirstDayVBox() throws ApiException, FileConvertingExceptions, CreatingObjectExcption {
        GetWheatherData weatherData = new GetWheatherData(cityNameTextField.getText());
        String currentDayData = weatherData.getCurrentDayWeatherForecast();
        ProcessWeatherData daylyWeatherData = new ProcessWeatherData(weatherData.collectDailyWeatherForecast(currentDayData));
        List<Weather> todaWeatherForecast = daylyWeatherData.getWeatherObject();

        VBox firsDay = prepareDailyBox(todaWeatherForecast);
        VBox firstNight = prepareDailyBox(todaWeatherForecast);
        List<VBox> firstDayVbox = new LinkedList<>();
        firstDayVbox.add(firsDay);
        firstDayVbox.add(firstNight);
        return firstDayVbox;
    }

    private void fillInWeatherMainWIndowWithData(List<Weather> data) throws ApiException, IOException, FileConvertingExceptions, CreatingObjectExcption {

            List<VBox> firstDayData = prepareFirstDayVBox();
            VBox firsDay = firstDayData.get(0);
            VBox firstNight = firstDayData.get(1);

            String secondDay = returnDateAsString(1);
            String thirdDay = returnDateAsString(2);
            String fourthDay = returnDateAsString(3);

            VBox secondDayVBox = prepareVBoxWithFillInLabels(data, dayHour, secondDay);
            VBox secondNIghtVBox = prepareVBoxWithFillInLabels(data, nightHour, secondDay);
            VBox thirdDayVBox = prepareVBoxWithFillInLabels(data, dayHour, thirdDay);
            VBox thirdNightVBox = prepareVBoxWithFillInLabels(data, nightHour, thirdDay);
            VBox fourthDayVBox = prepareVBoxWithFillInLabels(data, dayHour, fourthDay);
            VBox fourthNightVBox = prepareVBoxWithFillInLabels(data, nightHour, fourthDay);


            HBox firstHbox = new HBox();
            HBox secondHbox = new HBox();
            HBox thirdHbox = new HBox();
            HBox fourthHbox = new HBox();

            firstHbox.getChildren().addAll(firsDay, firstNight);
            secondHbox.getChildren().addAll(secondDayVBox, secondNIghtVBox);
            thirdHbox.getChildren().addAll(thirdDayVBox, thirdNightVBox);
            fourthHbox.getChildren().addAll(fourthDayVBox, fourthNightVBox);

            VBox mainVbox = new VBox();
            System.out.println("fillInWeatherMainWIndow before MAIN");
            mainVbox.getStylesheets().add(String.valueOf(Style.DARK));
            System.out.println("fillInWeatherMainWIndow after main");
            mainVbox.getChildren().addAll(firstHbox, secondHbox, thirdHbox, fourthHbox);
            setContentOfScrollPane(mainVbox);
    }

    private String returnDateAsString(int number){
        Date currentDay = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.setTime(currentDay);
        dayAfter.add(Calendar.DATE,number);
        return dateFormat.format(dayAfter.getTime());
    }

    private VBox prepareDailyBox(List<Weather> data){
        VBox mainBax = new VBox();
        //tutaj css oooooooooooooooooooooooooooooooooooooooooooooooo
        System.out.println("PrepareDailyBox1");
        mainBax.getStylesheets().add(String.valueOf(Style.DARK));
        for(int i =0; i<data.size(); i++) {

            WeatherClient weatherClient = (WeatherClient) data.get(i);
            String[] dateAsArray = weatherClient.splitDate(weatherClient.getDate());
            VBox weatherInfo = new VBox();
            //tutaj css oooooooooooooooooooooooooooooooooooooooooooooooo
            System.out.println("PrepareDailyBox2");
            weatherInfo.getStylesheets().add(String.valueOf(Style.DARK));
            HBox wrapBox = new HBox();
            System.out.println("PrepareDailyBox3");
            wrapBox.getStylesheets().add(String.valueOf(Style.DARK));
            Label date = new Label();
            Label temp = new Label();
            ImageView icon = new ImageView();
            Label description = new Label();
            Label humidity = new Label();
            Label pressure = new Label();
            Label feelsLike = new Label();
            date.setText("Day: " + dateAsArray[0]);
            temp.setText("Daily Tempture: " + weatherClient.getTemp() + " ℃");
            description.setText("Description: " + weatherClient.getDescription());
            humidity.setText("Humidity: " + weatherClient.getHumidity().toString() + " %");
            pressure.setText("Pressure: " + weatherClient.getPressure().toString() + " hPa");
            feelsLike.setText("Feels like: " + weatherClient.getFeelsLike() + " ℃");
            icon.setImage(weatherClient.prepareImageView());
            weatherInfo.getChildren().addAll(date, temp, description, humidity, pressure, feelsLike);
            wrapBox.getChildren().addAll(icon, weatherInfo);
            mainBax.getChildren().addAll(wrapBox);
        }
        return mainBax;
    }

    private VBox prepareVBoxWithFillInLabels(List<Weather> data, String hour, String year){

        VBox mainBax = new VBox();
        //tutaj css oooooooooooooooooooooooooooooooooooo
        System.out.println("PrepareVBox with fill in labels1");
        mainBax.getStylesheets().add(String.valueOf(Style.DARK));

        for(int i =0; i<data.size(); i++){

            WeatherClient weatherClient = (WeatherClient) data.get(i);
            String[] dateAsArray = weatherClient.splitDate(weatherClient.getDate());

            if(dateAsArray[1].endsWith(hour) && dateAsArray[0].startsWith(year)) {
                VBox weatherInfo = new VBox();
                HBox wrapBox = new HBox();
                //tutaj kolejny css oooooooooooooooooooooooooooooooo
                System.out.println("PrepareVBox with fill in labels2");
                wrapBox.getStylesheets().add(String.valueOf(Style.DARK));
                weatherInfo.getStylesheets().add(String.valueOf(Style.DARK));
                Label date = new Label();
                Label temp = new Label();
                ImageView icon = new ImageView();
                Label description = new Label();
                Label humidity = new Label();
                Label pressure = new Label();
                Label feelsLike = new Label();
                date.setText("Day: " + dateAsArray[0]);
                temp.setText("Daily Tempture: " + weatherClient.getTemp() + " ℃");
                description.setText("Description: " + weatherClient.getDescription());
                humidity.setText("Humidity: " + weatherClient.getHumidity().toString() + " %");
                pressure.setText("Pressure: " + weatherClient.getPressure().toString() + " hPa");
                feelsLike.setText("Feels like: " + weatherClient.getFeelsLike() + " ℃");
                icon.setImage(weatherClient.prepareImageView());
                weatherInfo.getChildren().addAll(date, temp, description, humidity, pressure, feelsLike);
                wrapBox.getChildren().addAll(icon, weatherInfo);
                mainBax.getChildren().addAll(wrapBox);
            }
        }
        VBox.setMargin(mainBax, new Insets(3,0,3,0));
        return mainBax;
    }

    private void setContentOfScrollPane(VBox vBox){
        System.out.println("before scroll pane");
        this.scrollPane.getStylesheets().add(String.valueOf(Style.DARK));
        this.scrollPane.setContent(vBox);
        System.out.println("after scroll pane");
        //tutaj css ooooooooooooooooooooooooooooooo

        this.cityNameLabel.setText(this.cityNameTextField.getText());
    }



    @FXML
    public void initialize(){
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        errorLabel.setText("");
    }

}
