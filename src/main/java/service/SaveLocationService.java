package service;

import dao.LocationsDao;
import dao.UsersDao;
import dto.SaveLocationDto;
import exceptions.UserNotFoundException;
import jakarta.persistence.NoResultException;
import model.Location;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SaveLocationService {
    private static final Logger logger = LogManager.getLogger(SaveLocationService.class);
    public void saveLocation(SaveLocationDto saveLocationDto) throws UserNotFoundException {
        Double lon = Double.parseDouble(saveLocationDto.getLon());
        Double lat = Double.parseDouble(saveLocationDto.getLat());
        User user = new UsersDao().findByLogin(saveLocationDto.getLogin())
                .orElseThrow(()->new UserNotFoundException("User is not found!"));
        Location location = new Location();
        List<User> users = new ArrayList<>();
        try {
            location = new LocationsDao().findByCoordinates(lat, lon);
            logger.info("Finding location: " + location);
            users = location.getUsers();
        } catch (NoResultException e) {
            location.setName(saveLocationDto.getCityName());
            location.setLongitude(lon);
            location.setLatitude(lat);
        }
        users.add(user);
        location.setUsers(users);
        logger.info("Location for saving: " + location);
        new LocationsDao().saveOrUpdate(location);
    }
}
