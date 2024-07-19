package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LocationsDao;
import dto.WeatherResponseDto;
import dto.elements.Coord;
import exceptions.ErrorApiConnectionException;
import model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherService  {
    private static final Logger logger = LogManager.getLogger(WeatherService.class);

    private LocationsDao locationsDao = new LocationsDao();

    public List<WeatherResponseDto> getWeather(String login) throws IOException, ErrorApiConnectionException {
        List<WeatherResponseDto> dtoList = new ArrayList<>();
        List<Location> locations = locationsDao.findByLogin(login);
        for (Location location : locations) {
            Coord coord = new Coord(String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()));
            URL url = new URL(Util.getCoordsApiUrl(coord));
            logger.info("ApiUrl: " + Util.getCoordsApiUrl(coord));
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
                    logger.info("responseData(json): " + responseData);
                    ObjectMapper objectMapper = new ObjectMapper();
                    WeatherResponseDto weatherResponseDto = objectMapper
                            .readValue(responseData.toString(), WeatherResponseDto.class);
                    weatherResponseDto.setLocationId(location.getId());
                    weatherResponseDto.setName(location.getName());
                    weatherResponseDto.setCoord(coord);
                    dtoList.add(weatherResponseDto);
                    logger.info("WeatherResponseDto single: " + weatherResponseDto);
                }
            } else {
                throw new ErrorApiConnectionException("Error of connection to WeatherAPI.");
            }
        }
        return dtoList;
    }
}
