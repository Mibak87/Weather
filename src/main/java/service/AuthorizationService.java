package service;

import dao.SessionsDao;
import dao.UsersDao;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import model.User;
import model.UserSession;
import org.mindrot.jbcrypt.BCrypt;

public class AuthorizationService {
    public void authorize(UserSession userSession) throws UserNotFoundException {
        if (!new UsersDao().checkByLogin(userSession.getUser().getLogin())) {
            throw new UserNotFoundException("This user not found!");
        }
        User user = new UsersDao().findByLogin(userSession.getUser().getLogin());
        if (!BCrypt.checkpw(userSession.getUser().getPassword(),user.getPassword())) {
            throw new WrongPasswordException("The password is wrong!");
        }
        userSession.setUser(user);
        new SessionsDao().saveOrUpdate(userSession);
    }
}
