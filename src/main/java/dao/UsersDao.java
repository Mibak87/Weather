package dao;

import model.Location;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class UsersDao {
    public Optional<User> findByLogin(String login) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM User WHERE login = :login";
            User user = session.createQuery(query, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return Optional.ofNullable(user);
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

    public boolean checkByPassword(String password) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM User WHERE password = :password";
            List<User> user = session.createQuery(query, User.class)
                    .setParameter("password", password)
                    .getResultList();
            if (user.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }
}
