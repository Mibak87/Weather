package service;

import dao.LocationsDao;
import dao.UsersDao;
import dto.SaveLocationDto;
import exceptions.UserNotFoundException;
import model.Location;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
    private static final Logger logger = LogManager.getLogger(LocationService.class);

    private UsersDao usersDao = new UsersDao();
    private LocationsDao locationsDao = new LocationsDao();

    public void saveLocation(SaveLocationDto saveLocationDto) throws UserNotFoundException {
        Double lon = Double.parseDouble(saveLocationDto.getLon());
        Double lat = Double.parseDouble(saveLocationDto.getLat());
        User user = usersDao.findByLogin(saveLocationDto.getLogin());
        Location location = new Location();
        location.setName(saveLocationDto.getCityName());
        location.setLongitude(lon);
        location.setLatitude(lat);
        location.setUser(user);
        logger.info("Location for saving: " + location);
        try {
            locationsDao.save(location);
        } catch (Exception e) {}
    }

    public void deleteLocation(long locationId) {
        Location location = locationsDao.findById(locationId);
        logger.info("Location for delete: " + location);
        locationsDao.delete(locationId);
    }
}
