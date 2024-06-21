package service;

import controller.AuthorizationController;
import dao.SessionsDao;
import exceptions.UserAlreadyExistsException;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RegistrationService {
    private static final Logger logger = LogManager.getLogger(RegistrationService.class);
    public void registration (UserSession userSession) throws UserAlreadyExistsException {
        new SessionsDao().save(userSession);
        List<UserSession> userSessions = new SessionsDao().findAll();
        logger.info(userSessions.toString());
    }
}
