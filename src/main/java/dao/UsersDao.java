package dao;

import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;

public class UsersDao {
    public User findByLogin(String login) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM User WHERE login = :login";
            User user = session.createQuery(query, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return user;
        }
    }

    public boolean checkByLogin(String login) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM User WHERE login = :login";
            List<User> user = session.createQuery(query, User.class)
                    .setParameter("login", login)
                    .getResultList();
            if (user.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }
}
