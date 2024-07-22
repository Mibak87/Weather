package service;

import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import model.User;
import model.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class AuthorizationServiceTest {
    private AuthorizationService authorizationService;
    @BeforeEach
    void init() {
        String password = BCrypt.hashpw("123", BCrypt.gensalt());
        User user = new User("Vasya",password);
        Date date = new Date(new Date().getTime()-5000);
        UserSession userSession = new UserSession("1",user, date);
        new RegistrationService().registration(userSession);
        authorizationService = new AuthorizationService();
    }

    @Test
    void authorization_nonExistentUser_UserNotFoundException() {
        User nonExistenUser = new User("Petya","123");
        Date date = new Date(new Date().getTime());
        UserSession newUserSession = new UserSession("1",nonExistenUser, date);
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            authorizationService.authorize(newUserSession);
        });
    }

    @Test
    void authorization_wrongPassword_WrongPasswordException() {
        User userWithWrongPassword = new User("Vasya","234");
        Date date = new Date(new Date().getTime());
        UserSession newUserSession = new UserSession("1",userWithWrongPassword, date);
        Assertions.assertThrows(WrongPasswordException.class, () -> {
            authorizationService.authorize(newUserSession);
        });
    }
}
