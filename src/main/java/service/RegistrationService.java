package service;

import dao.SessionsDao;
import dto.RegistrationDto;
import model.User;
import model.UserSession;
import utils.Util;

import java.util.Date;

public class RegistrationService {
    private final int HOURS_BEFORE_SESSION_TIME_EXPIRES = 5;
    public void registration (RegistrationDto registrationDto) {
        User user = new User(registrationDto.getLogin(), registrationDto.getPassword());
        Date expiresAt = Util.getExpiryDate(HOURS_BEFORE_SESSION_TIME_EXPIRES);
        UserSession userSession = new UserSession(registrationDto.getId(), user, expiresAt);
        new SessionsDao().save(userSession);
    }
}
