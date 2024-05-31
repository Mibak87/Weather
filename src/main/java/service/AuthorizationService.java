package service;

import dao.SessionsDao;
import dao.UsersDao;
import exceptions.UserNotFoundException;
import model.User;
import model.UserSession;

public class AuthorizationService {
    public void authorize(UserSession userSession) throws UserNotFoundException {
        User user = new UsersDao().findByLogin(userSession.getUser().getLogin())
                    .orElseThrow(() -> new UserNotFoundException("This user not found!"));
        new SessionsDao().save(userSession);
    }
}
