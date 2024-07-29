package service;

import dao.LocationsDao;
import dao.UsersDao;
import dto.SaveLocationDto;
import dto.elements.Coord;
import exceptions.UserNotFoundException;
import model.Location;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationService {
    private static final Logger logger = LogManager.getLogger(LocationService.class);

    private final UsersDao usersDao = new UsersDao();
    private final LocationsDao locationsDao = new LocationsDao();

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
        } catch (Exception e) {
            logger.info("Error of location saving.");
        }
    }

    public void deleteLocation(Coord coord, String userLogin) {
        Double latitude = Double.parseDouble(coord.getLat());
        Double longitude = Double.parseDouble(coord.getLon());
        User user = usersDao.findByLogin(userLogin);
        locationsDao.delete(latitude,longitude,user);
    }
}
