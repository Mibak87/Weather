package service;

import dao.SessionsDao;
import dao.UsersDao;
import exceptions.UserNotFoundException;
import model.UserSession;

public class AuthorizationService {
    public void authorize(UserSession userSession) throws UserNotFoundException {
        if (!new UsersDao().checkByLogin(userSession.getUser().getLogin())) {
            throw new UserNotFoundException("This user not found!");
        }
        new SessionsDao().saveOrUpdate(userSession);
    }
}
