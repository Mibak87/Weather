package service;

import dao.SessionsDao;
import dto.RegistrationDto;
import exceptions.UserAlreadyExistsException;
import model.User;
import model.UserSession;
import utils.Util;

import java.util.List;

public class RegistrationService {
    public void registration (RegistrationDto registrationDto) throws UserAlreadyExistsException {
        User user = new User(registrationDto.getLogin(), registrationDto.getPassword());
        UserSession userSession = new UserSession(registrationDto.getId(), user, Util.getExpiryDate());
        new SessionsDao().save(userSession);
        List<UserSession> userSessions = new SessionsDao().findAll();
        System.out.println(userSessions.get(0).toString());
    }
}
