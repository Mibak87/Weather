package service;

import dao.SessionsDao;
import dao.UsersDao;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import model.User;
import model.UserSession;
import org.mindrot.jbcrypt.BCrypt;

public class AuthorizationService {
    private UsersDao usersDao = new UsersDao();
    private SessionsDao sessionsDao = new SessionsDao();
    public void authorize(UserSession userSession) throws UserNotFoundException {
        if (!usersDao.checkByLogin(userSession.getUser().getLogin())) {
            throw new UserNotFoundException("This user not found!");
        }
        User user = usersDao.findByLogin(userSession.getUser().getLogin());
        if (!BCrypt.checkpw(userSession.getUser().getPassword(),user.getPassword())) {
            throw new WrongPasswordException("The password is wrong!");
        }
        userSession.setUser(user);
        sessionsDao.saveOrUpdate(userSession);
    }
}
