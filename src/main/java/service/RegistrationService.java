package service;

import dao.SessionsDao;
import exceptions.UserAlreadyExistsException;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RegistrationService {
    private static final Logger logger = LogManager.getLogger(RegistrationService.class);
    public void registration (UserSession userSession) throws UserAlreadyExistsException {
        new SessionsDao().save(userSession);
        logger.info(new SessionsDao().findAll().toString());
    }
}
