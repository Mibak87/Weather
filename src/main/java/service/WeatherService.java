package service;

import dao.LocationsDao;
import dto.WeatherResponseDto;
import dto.elements.Coord;
import exceptions.ErrorApiConnectionException;
import model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WeatherService  {
    private static final Logger logger = LogManager.getLogger(WeatherService.class);

    private final LocationsDao locationsDao = new LocationsDao();

    public List<WeatherResponseDto> getWeather(String login) throws ErrorApiConnectionException {
        List<WeatherResponseDto> dtoList = new ArrayList<>();
        List<Location> locations = locationsDao.findByLogin(login);
        for (Location location : locations) {
            Coord coord = new Coord(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
            WeatherResponseDto weatherResponseDto = new OpenWeatherApiService().getWeather(coord);
            weatherResponseDto.setLocationId(location.getId());
            weatherResponseDto.setName(location.getName());
            weatherResponseDto.setCoord(coord);
            dtoList.add(weatherResponseDto);
            logger.info("WeatherResponseDto single: " + weatherResponseDto);
        }
        return dtoList;
    }
}
