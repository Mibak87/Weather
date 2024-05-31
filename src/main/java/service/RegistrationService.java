package service;

import dao.SessionsDao;
import dto.RegistrationDto;
import model.User;
import model.UserSession;
import utils.Util;

import java.util.Date;
import java.util.List;

public class RegistrationService {
    public void registration (RegistrationDto registrationDto) {
        User user = new User(registrationDto.getLogin(), registrationDto.getPassword());
        Date expiresAt = Util.getExpiryDate();
        UserSession userSession = new UserSession(registrationDto.getId(), user, expiresAt);
        new SessionsDao().save(userSession);
        List<UserSession> userSessions = new SessionsDao().findAll();
        System.out.println(userSessions.get(0).toString());
    }
}
