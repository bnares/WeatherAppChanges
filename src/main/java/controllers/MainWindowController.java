package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.GetWheatherData;
import view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public String getCityNameTextField(){
        return this.cityNameTextField.getText();
    }

    @FXML
    void findCityButton() {

        if (cityNameTextField.getText().length()<=0 || cityNameTextField.getText().isEmpty()){
            errorLabel.setText("Fill in city name box");
        }else {
            GetWheatherData weatherData = new GetWheatherData(cityNameTextField.getText());
            String data = weatherData.getFourDaysWheatherData();
            ProcessWeatherData processWeatherData = new ProcessWeatherData(weatherData.collectWeatherDataFourDaysInCollectionForm(data));
            List<Weather> objectWeatherData = processWeatherData.createWeatherClientObject();
            fillInWeatherMainWIndowWithData(objectWeatherData);
        }
    }

    private void fillInWeatherMainWIndowWithData(List<Weather> data){
        if(data.size()<2){
            errorLabel.setText(data.get(0).getError());
        }else {
            GetWheatherData weatherData = new GetWheatherData(cityNameTextField.getText());
            String currentDayData = weatherData.getCurrentDayWeatherForecast();
            ProcessWeatherData daylyWeatherData = new ProcessWeatherData(weatherData.collectDailyWeatherForecast(currentDayData));
            List<Weather> todaWeatherForecast = daylyWeatherData.createWeatherClientObject();
            VBox firsDay = prepareDailyBox(todaWeatherForecast);
            VBox firstNight = prepareDailyBox(todaWeatherForecast);
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
            mainVbox.getChildren().addAll(firstHbox, secondHbox, thirdHbox, fourthHbox);
            setContentOfScrollPane(mainVbox);

        }
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
        for(int i =0; i<data.size(); i++) {

            WeatherClient weatherClient = (WeatherClient) data.get(i);
            String[] dateAsArray = weatherClient.splitDate(weatherClient.getDate());
            VBox weatherInfo = new VBox();
            HBox wrapBox = new HBox();
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

        for(int i =0; i<data.size(); i++){

            WeatherClient weatherClient = (WeatherClient) data.get(i);
            String[] dateAsArray = weatherClient.splitDate(weatherClient.getDate());

            if(dateAsArray[1].endsWith(hour) && dateAsArray[0].startsWith(year)) {
                VBox weatherInfo = new VBox();
                HBox wrapBox = new HBox();
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
        return mainBax;
    }

    private void setContentOfScrollPane(VBox vBox){
        this.scrollPane.setContent(vBox);
        this.cityNameLabel.setText(this.cityNameTextField.getText());
    }

    @FXML
    public void initialize(){
        errorLabel.setText("");
    }

}
