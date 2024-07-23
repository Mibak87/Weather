package service;

import dto.CitiesResponseDto;
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

    private String citiesResponse = "[{\"name\":\"Moscow\",\"local_names\":{\"ru\":\"Москва\"},\"lat\":55.7504461,\"lon\":37.6174943,\"country\":\"RU\",\"state\":\"Moscow\"}]";

    @Test
    void getCities_MoscowCity() throws InterruptedException, IOException {
        String location = "Moscow";
        Mockito.when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);
        Mockito.when(response.body()).thenReturn(citiesResponse);
        CitiesResponseDto[] citiesResponseDto = openWeatherApiService.getCities(location);
        Assertions.assertEquals(location,citiesResponseDto[0].getName());
    }
}
