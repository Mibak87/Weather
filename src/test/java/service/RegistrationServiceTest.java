package service;

import model.User;
import model.UserSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HibernateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RegistrationServiceTest {

    private UserSession userSession;
    @BeforeEach
    void init() {
        User user = new User("Vasya","123");
        Date date = new Date(new Date().getTime()-5000);
        userSession = new UserSession("1",user, date);
    }

    @Test
    void registration_saveUserSession_saveUser() {
        new RegistrationService().registration(userSession);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = session.createQuery("FROM User", User.class).getResultList();
        session.close();
        assertEquals(1, userList.size());
    }

    /*@Test
    void registration_saveUserSessionWithNotUniqueUser_UserAlreadyExistsException() {
        Session session = HibernateH2Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(userSession);
        transaction.commit();
        User notUniqueUser = new User("Vasya","456");
        Date date = new Date(new Date().getTime());
        UserSession newUserSession = new UserSession("1",notUniqueUser, date);
        transaction = session.beginTransaction();
        session.persist(newUserSession);
        transaction.commit();
        session.close();
    }*/
}
