package service;

import dao.SessionsDao;
import dao.UsersDao;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import model.User;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class AuthorizationService {
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);
    private final UsersDao usersDao = new UsersDao();
    private final SessionsDao sessionsDao = new SessionsDao();
    public void authorize(UserSession userSession) throws UserNotFoundException {
        if (!usersDao.checkByLogin(userSession.getUser().getLogin())) {
            throw new UserNotFoundException("This user not found!");
        }
        User user = usersDao.findByLogin(userSession.getUser().getLogin());
        logger.info("Password from Data Base: " + user.getPassword());
        logger.info("Password by User: " + userSession.getUser().getPassword());
        if (!BCrypt.checkpw(userSession.getUser().getPassword(),user.getPassword())) {
            throw new WrongPasswordException("The password is wrong!");
        }
        userSession.setUser(user);
        sessionsDao.saveOrUpdate(userSession);
    }
}
