package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CitiesResponseDto;
import dto.WeatherResponseDto;
import dto.elements.Coord;
import exceptions.ErrorApiConnectionException;
import utils.Util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenWeatherApiService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenWeatherApiService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CitiesResponseDto[] getCities(String location) throws ErrorApiConnectionException {
        try {
            URI uri = new URI(Util.getCitiesApiUrl(location));
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), CitiesResponseDto[].class);
        } catch (Exception e) {
            throw new ErrorApiConnectionException("Error of API connection.");
        }
    }

    public WeatherResponseDto getWeather(Coord coord) throws ErrorApiConnectionException {
        try {
            URI uri = new URI(Util.getCoordsApiUrl(coord));
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), WeatherResponseDto.class);
        } catch (Exception e) {
            throw new ErrorApiConnectionException("Error of API connection.");
        }

    }
}
