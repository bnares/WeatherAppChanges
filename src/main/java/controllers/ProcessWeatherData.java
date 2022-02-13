package controllers;

import exception.CreatingObjectExcption;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProcessWeatherData {

    private List<Map> introData;

    public ProcessWeatherData(List<Map> data){
        this.introData = data;
    }

    public List<Weather> getWeatherObject() throws CreatingObjectExcption {
        return this.createWeatherClientObject();
    }

    private List<Weather> createWeatherClientObject() throws CreatingObjectExcption {

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

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            throw new CreatingObjectExcption();
        }
        return object;
    }
}
