package model;

import com.sun.javafx.collections.MappingChange;
import exception.ApiException;
import exception.FileConvertingExceptions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ModelTest {

    private GetWheatherData getWheatherData = mock(GetWheatherData.class);
    @Test
    void collectDailyWeatherForecastShouldReturnMapCollection() throws FileConvertingExceptions {
        //given

        Map hashMap = new HashMap();
        Map hashMap1 = new HashMap();
        List<Map> data = new LinkedList<>();
        //when
        hashMap.put("main", "pogoda");
        hashMap.put("weather", "12");
        hashMap1.put("test", "dwa");
        data.add(hashMap);
        data.add(hashMap1);
        given(getWheatherData.collectDailyWeatherForecast(any(String.class))).willReturn(data);
        //then
        List<Map> finalData = getWheatherData.collectDailyWeatherForecast("test");
        assertThat(finalData, hasSize(2));

    }

    @Test
    void collectDailyWeatherForecastShouldThrowException() throws FileConvertingExceptions{
        given(getWheatherData.collectDailyWeatherForecast(any(String.class))).willThrow(FileConvertingExceptions.class);
        assertThrows(FileConvertingExceptions.class, ()-> getWheatherData.collectDailyWeatherForecast("trst"));
    }

    @Test
    void collectWeatherDataFourDaysInCollectionFormShouldReturnMapCollection() throws FileConvertingExceptions {
        //given
        List<Map> weatherClientsDayData = new LinkedList<>();
        Map hashMap = new HashMap();
        //when
        hashMap.put("klucz", "wartosc");
        weatherClientsDayData.add(hashMap);
        given(getWheatherData.collectWeatherDataFourDaysInCollectionForm(any(String.class))).willReturn(weatherClientsDayData);
        //then
        List<Map> data = getWheatherData.collectWeatherDataFourDaysInCollectionForm("test");
        assertThat(data, hasSize(1));

    }

    @Test
    void collectWeatherDataFourDaysInCollectionFormShouldThrowError() throws FileConvertingExceptions {
        given(getWheatherData.collectWeatherDataFourDaysInCollectionForm(any(String.class))).willThrow(FileConvertingExceptions.class);
        assertThrows(FileConvertingExceptions.class, ()->getWheatherData.collectWeatherDataFourDaysInCollectionForm("test"));
    }

    @Test
    void getCurrentDayWeatherForecastShouldReturnString() throws ApiException {
        //given
        //when
        String city = getWheatherData.getCityName();
        Object ob = "{\"coord\":{\"lon\":21.0118,\"lat\":52.2298},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":3.61,\"feels_like\":-1.54,\"temp_min\":2.88,\"temp_max\":4.39,\"pressure\":1015,\"humidity\":86},\"visibility\":10000,\"wind\":{\"speed\":7.72,\"deg\":260},\"clouds\":{\"all\":75},\"dt\":1644314337,\"sys\":{\"type\":2,\"id\":2035775,\"country\":\"PL\",\"sunrise\":1644300317,\"sunset\":1644334503},\"timezone\":3600,\"id\":756135,\"name\":\"Warsaw\",\"cod\":200}";
        given(getWheatherData.getCityName()).willReturn("Warszawa");
        given(getWheatherData.getCurrentDayWeatherForecast()).willReturn((String) ob);
        //then
        assertThat(getWheatherData.getCurrentDayWeatherForecast(), equalTo(ob));
    }

    

}
