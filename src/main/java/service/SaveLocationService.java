package service;

import dao.UsersDao;
import dto.SaveLocationDto;
import exceptions.UserNotFoundException;
import model.User;

public class SaveLocationService {
    public void saveLocation(SaveLocationDto saveLocationDto) throws UserNotFoundException {
        double lon = Double.parseDouble(saveLocationDto.getLon());
        double lat = Double.parseDouble(saveLocationDto.getLat());
        User user = new UsersDao().findByLogin(saveLocationDto.getLogin())
                .orElseThrow(()->new UserNotFoundException("User is not found!"));

    }
}
