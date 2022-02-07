package controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProcessWeatherData {

    private List<Map> introData;

    public ProcessWeatherData(List<Map> data){
        this.introData = data;
    }

    protected List<Weather> createWeatherClientObject(){

        List<Weather> object = new LinkedList<>();
        try{
            for(int i =0; i<this.introData.size(); i++){
                String date = this.introData.get(i).get("date").toString();
                String icon = (String) this.introData.get(i).get("icon");
                String description = (String) this.introData.get(i).get("description");
                String temp =  this.introData.get(i).get("temp").toString();
                Integer humidity = (Integer)  this.introData.get(i).get("humidity");
                Integer pressure = (Integer)  this.introData.get(i).get("pressure");
                String feelsLike = this.introData.get(i).get("feelsLike").toString();
                WeatherClient weatherClient = new WeatherClient(date, icon, description,temp,humidity,pressure,feelsLike);
                object.add(weatherClient);
            }

        }catch (Exception e){
            //String error =(String) this.introData.get(0).get("error");
            System.out.println(e.getMessage());
            //ErrorMessages errorMessages = new ErrorMessages();
            //errorMessages.setError(error);
            //object.add(errorMessages);
            //return object;
        }
        return object;
    }
}
