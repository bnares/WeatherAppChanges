package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherClient extends Weather{

    private final String date;
    private final String icon;
    private final String description;
    private final String temp;
    private final Integer humidity;
    private final Integer pressure;
    private final String feelsLike;
    private String imageUrl;

    public WeatherClient(String date, String icon, String description,
                   String temp, Integer humidity, Integer pressure, String feelsLike) {
        DecimalFormat df = setNumberRunding();
        Double tempToDouble = Double.parseDouble(temp);
        Double temtureDouble = Double.valueOf(df.format(tempToDouble));
        Double feelsLikeTempture = Double.valueOf(Double.parseDouble(feelsLike));
        this.date = date;
        this.icon = icon;
        this.description = description;
        this.temp = Integer.toString(temtureDouble.intValue());
        this.humidity = humidity;
        this.pressure = pressure;
        this.feelsLike = Integer.toString(feelsLikeTempture.intValue());
        this.imageUrl = "https://openweathermap.org/img/wn/"+icon+"@2x.png";

    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    protected DecimalFormat setNumberRunding(){
        DecimalFormat df = new DecimalFormat("#");
        return df;
    }

    protected String[] splitDate(String date){
        return date.split(" ");
    }

    protected Image prepareImageView(){
        Image imageView = new Image(imageUrl);
        return imageView;
    }

    @Override
    public String toString() {
        return "WeatherClient{" +
                "date='" + date + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", feelsLike='" + feelsLike + '\'' +
                '}';
    }
}
