package service;

import dao.SessionsDao;
import dao.UsersDao;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import model.UserSession;

public class AuthorizationService {
    public void authorize(UserSession userSession) throws UserNotFoundException {
        if (!new UsersDao().checkByLogin(userSession.getUser().getLogin())) {
            throw new UserNotFoundException("This user not found!");
        } else if (!new UsersDao().checkByPassword(userSession.getUser().getPassword())) {
            throw new WrongPasswordException("The password is wrong!");
        }
        userSession.setUser(new UsersDao().findByLogin(userSession.getUser().getLogin()).get());
        new SessionsDao().saveOrUpdate(userSession);
    }
}
