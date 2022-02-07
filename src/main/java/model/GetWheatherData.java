package model;

import exception.ApiException;
import exception.FileConvertingExceptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GetWheatherData {
    private String cityName;

    public GetWheatherData(String city){
        this.cityName = city;
    }

    private String getCurrentDayWeatherData() throws ApiException {
        StringBuffer response;
        String textField = cityName;
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(textField, StandardCharsets.UTF_8) + "&appid=ac3ab545014509bfe6bd90e10adf9a94&cnt=46&units=metric";
        response = connectWithNetWebForWheatherData(url);

        return response.toString();
    }

    public String getCurrentDayWeatherForecast() throws ApiException {
        return getCurrentDayWeatherData();

    }

    private String sendRequestForFourDaysWheatherData() throws ApiException {

        StringBuffer response;
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+ URLEncoder.encode(this.cityName, StandardCharsets.UTF_8)+"&appid=ac3ab545014509bfe6bd90e10adf9a94&cnt=46&units=metric";
        response = connectWithNetWebForWheatherData(url);
        return response.toString();

    }

    public String getFourDaysWheatherData() throws ApiException {
        return sendRequestForFourDaysWheatherData();

    }

    private StringBuffer connectWithNetWebForWheatherData(String url) throws ApiException {
        URL adress= null;
        StringBuffer response = new StringBuffer();
        try {
            adress = new URL(url);
            URLConnection con = adress.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            int responseCOde = http.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(http.getInputStream())
            );
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println(response);
        } catch (IOException e){
            throw new ApiException();
        }

        return response;
    }

    public List<Map> collectDailyWeatherForecast(String weatherData) throws FileConvertingExceptions{
        List<Map> weatherClientsDayData = new LinkedList<>();
        try{
            JSONObject jsonObject = new JSONObject(weatherData);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            Map dayInfo = new HashMap();

            JSONObject main = (JSONObject) jsonObject.get("main");
            dayInfo.put("date", new Date());
            dayInfo.put("temp",main.get("temp"));
            JSONObject weatherJsonObject = (JSONObject) jsonArray.get(0);
            dayInfo.put("icon", weatherJsonObject.get("icon"));
            dayInfo.put("description", weatherJsonObject.get("description"));
            dayInfo.put("humidity", main.get("humidity"));
            dayInfo.put("pressure", main.get("pressure"));
            dayInfo.put("feelsLike", main.get("feels_like"));
            weatherClientsDayData.add(dayInfo);

        }catch (JSONException e){
            System.out.println(e.getMessage());
            throw new FileConvertingExceptions();

        }
        return weatherClientsDayData;
    }

    public List<Map> collectWeatherDataFourDaysInCollectionForm(String weatherData) throws FileConvertingExceptions{
        List<Map> weatherClientsDayData = new LinkedList<>();
        try {
            JSONObject jsonObject = new JSONObject(weatherData);
            JSONArray array = jsonObject.getJSONArray("list");

            for (int i = 0; i < array.length(); i++) {
                Map dayInfo = new HashMap();
                JSONObject listJsonObject = (JSONObject) array.get(i);
                if(listJsonObject.get("dt_txt").toString().endsWith("12:00:00") || listJsonObject.get("dt_txt").toString().endsWith("21:00:00")) {
                    dayInfo.put("date", listJsonObject.get("dt_txt"));
                    JSONArray test = listJsonObject.getJSONArray("weather");
                    for (int j = 0; j < test.length(); j++) {
                        JSONObject nestedWeatherDescriptionList = (JSONObject) test.get(j);
                        dayInfo.put("icon", nestedWeatherDescriptionList.get("icon"));
                        dayInfo.put("description", nestedWeatherDescriptionList.get("description"));
                    }
                    JSONObject main = (JSONObject) listJsonObject.get("main");
                    dayInfo.put("temp", main.get("temp"));
                    dayInfo.put("humidity", main.get("humidity"));
                    dayInfo.put("pressure", main.get("pressure"));
                    dayInfo.put("feelsLike", main.get("feels_like"));
                    weatherClientsDayData.add(dayInfo);
                }
            }

        } catch (JSONException e){
            System.out.println(e.getMessage());
            throw new FileConvertingExceptions();
        }
        return weatherClientsDayData;
    }
}
