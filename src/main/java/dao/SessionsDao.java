package dao;

import model.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class SessionsDao {

    public void save(UserSession userSession) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(userSession);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<UserSession> findAll() throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserSession ", UserSession.class).getResultList();
        }
    }

    public Optional<UserSession> findById(String id) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            UserSession userSession = session.get(UserSession.class,id);
            return Optional.ofNullable(userSession);
        }
    }
}
