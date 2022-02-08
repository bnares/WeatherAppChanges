package controllers;

import exception.CreatingObjectExcption;
import org.junit.jupiter.api.Test;
import view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ProcessWeatherDataTest {


    private WeatherClient weatherClient = mock(WeatherClient.class);
    private ProcessWeatherData processWeatherData = mock(ProcessWeatherData.class);

    @Test
    void getWeatherObjectShouldReturnWeatherObjectList() throws CreatingObjectExcption {

        //given
        List<Weather> weatherList = new LinkedList<>();
        //when
        weatherList.add(weatherClient);
        given(processWeatherData.getWeatherObject()).willReturn(weatherList);
        //then
        assertThat(processWeatherData.getWeatherObject(), hasSize(1));

    }

    @Test
    void getWeatherObjectShouldThrowException() throws CreatingObjectExcption {
        //given
        //when
        given(processWeatherData.getWeatherObject()).willThrow(CreatingObjectExcption.class);
        //then
        assertThrows(CreatingObjectExcption.class, ()->processWeatherData.getWeatherObject());
    }


}
