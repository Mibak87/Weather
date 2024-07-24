package service;

import dto.CitiesResponseDto;
import dto.WeatherResponseDto;
import dto.elements.Coord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherApiTest {
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse<String> response;
    @InjectMocks
    private OpenWeatherApiService openWeatherApiService;

    private String citiesResponse = "[{\"name\":\"Moscow\",\"local_names\":{\"ru\":\"Москва\"},"
            + "\"lat\":55.7504461,\"lon\":37.6174943,\"country\":\"RU\",\"state\":\"Moscow\"}]";
    private final String weatherResponse = """
            {
              "coord": {
                "lon": 37.6175,
                "lat": 55.7504
              },
              "weather": [
                {
                  "id": 801,
                  "main": "Clouds",
                  "description": "few clouds",
                  "icon": "02d"
                }
              ],
              "base": "stations",
              "main": {
                "temp": 24.49,
                "feels_like": 23.91,
                "temp_min": 23.8,
                "temp_max": 25.1,
                "pressure": 1013,
                "humidity": 35,
                "sea_level": 1013,
                "grnd_level": 994
              },
              "visibility": 10000,
              "wind": {
                "speed": 2.59,
                "deg": 31,
                "gust": 3.74
              },
              "clouds": {
                "all": 16
              },
              "dt": 1721814778,
              "sys": {
                "type": 1,
                "id": 9027,
                "country": "RU",
                "sunrise": 1721784045,
                "sunset": 1721843460
              },
              "timezone": 10800,
              "id": 524901,
              "name": "Moscow",
              "cod": 200
            }""";

    @Test
    void getCities_MoscowCity() throws InterruptedException, IOException {
        String location = "Moscow";
        Mockito.when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);
        Mockito.when(response.body()).thenReturn(citiesResponse);
        CitiesResponseDto[] citiesResponseDto = openWeatherApiService.getCities(location);
        Assertions.assertEquals(location,citiesResponseDto[0].getName());
    }

    @Test
    void getWeather_requestMoscowCoord_MoscowTemperature() throws InterruptedException, IOException {
        Coord coord = new Coord("37.6174943","55.7504461");
        Mockito.when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);
        Mockito.when(response.body()).thenReturn(weatherResponse);
        WeatherResponseDto weatherResponseDto = openWeatherApiService.getWeather(coord);
        Assertions.assertEquals(24.49,weatherResponseDto.getMain().getTemp());
    }

}
