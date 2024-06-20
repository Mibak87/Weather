package service;

import dao.LocationsDao;
import dao.UsersDao;
import dto.SaveLocationDto;
import exceptions.UserNotFoundException;
import model.Location;
import model.User;

import java.util.List;

public class SaveLocationService {
    public void saveLocation(SaveLocationDto saveLocationDto) throws UserNotFoundException {
        Double lon = Double.parseDouble(saveLocationDto.getLon());
        Double lat = Double.parseDouble(saveLocationDto.getLat());
        User user = new UsersDao().findByLogin(saveLocationDto.getLogin())
                .orElseThrow(()->new UserNotFoundException("User is not found!"));
        Location location = new LocationsDao().findByCoordinates(lat,lon).orElseThrow();
        List<User> users = location.getUsers();
        users.add(user);
        location.setUsers(users);
        new LocationsDao().saveOrUpdate(location);
    }
}
