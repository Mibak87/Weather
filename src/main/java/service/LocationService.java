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

public class LocationService {
    private static final Logger logger = LogManager.getLogger(LocationService.class);

    private UsersDao usersDao = new UsersDao();
    private LocationsDao locationsDao = new LocationsDao();

    public void saveLocation(SaveLocationDto saveLocationDto) throws UserNotFoundException {
        Double lon = Double.parseDouble(saveLocationDto.getLon());
        Double lat = Double.parseDouble(saveLocationDto.getLat());
        User user = usersDao.findByLogin(saveLocationDto.getLogin());
        Location location = new Location();
        List<User> users = new ArrayList<>();
        try {
            location = locationsDao.findByCoordinates(lat, lon);
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
        locationsDao.saveOrUpdate(location);
    }

    public void deleteLocation(long locationId, String userLogin) {
        User user = usersDao.findByLogin(userLogin);
        Location location = locationsDao.findById(locationId);
        logger.info("Location for delete: " + location);
        List<User> users = location.getUsers();
        if (users.size() > 1) {
            users.remove(user);
            location.setUsers(users);
            locationsDao.saveOrUpdate(location);
        } else {
            locationsDao.delete(locationId);
        }
    }
}
