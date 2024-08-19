package dao;

import exceptions.UserAlreadyExistsException;
import jakarta.persistence.PersistenceException;
import model.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import utils.HibernateUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SessionsDao {

    public void save(UserSession userSession) throws UserAlreadyExistsException {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            try {
                session.persist(userSession);
                transaction.commit();
            } catch (PersistenceException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                System.out.println("ConstraintViolationException");
                throw new UserAlreadyExistsException("The User already exists!");
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

    public void saveOrUpdate(UserSession userSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(userSession);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserSession userSession = session.get(UserSession.class,id);
            session.remove(userSession);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    public void deleteExpiredSessions(Date date) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM UserSession WHERE expiresAt <= : date", UserSession.class)
            .setParameter("date",date);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
