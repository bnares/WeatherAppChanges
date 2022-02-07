package model;

import com.sun.javafx.collections.MappingChange;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ModelTest {

    private GetWheatherData getWheatherData = mock(GetWheatherData.class);
    @Test
    void collectDailyWeatherForecastShouldReturnMapCollection() throws Exception {
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
    void collectDailyWeatherForecastShouldThrowException(){
        //given(getWheatherData.collectDailyWeatherForecast(any(String.class))).willThrow(Exception.class);
        //assertThrows(Exception.class,()->);
    }

}
