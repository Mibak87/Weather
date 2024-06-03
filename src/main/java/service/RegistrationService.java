package service;

import dao.SessionsDao;
import exceptions.UserAlreadyExistsException;
import model.UserSession;

import java.util.List;

public class RegistrationService {
    public void registration (UserSession userSession) throws UserAlreadyExistsException {
        new SessionsDao().save(userSession);
        List<UserSession> userSessions = new SessionsDao().findAll();
        System.out.println(userSessions.toString());
    }
}
