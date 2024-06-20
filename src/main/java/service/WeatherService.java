package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LocationsDao;
import dto.CitiesResponseDto;
import dto.WeatherResponseDto;
import dto.elements.Coord;
import model.Location;
import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    public List<Coord> getCoordinates(String login) {
        List<Location> locations = new LocationsDao().findByLogin(login);
        List<Coord> coords = new ArrayList<>();
        for (Location location : locations) {
            Coord coord = new Coord(String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()));
            coords.add(coord);
        }
        return coords;
    }

    public List<WeatherResponseDto> getWeather(String login) throws IOException {
        List<WeatherResponseDto> dtoList = new ArrayList<>();
        List<Coord> coords = getCoordinates(login);
        for (Coord coord : coords) {
            URL url = new URL(Util.getCoordsApiUrl(coord));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuffer responseData = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        responseData.append(inputLine);
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    WeatherResponseDto weatherResponseDto = objectMapper
                            .readValue(responseData.toString(), WeatherResponseDto.class);
                    dtoList.add(weatherResponseDto);
                }
            } else {
                //Обработка ошибки
            }
        }
        return dtoList;
    }
}
