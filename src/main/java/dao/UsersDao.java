package dao;

import jakarta.persistence.NoResultException;
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
}
