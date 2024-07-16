package dao;

import model.Location;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;

public class LocationsDao {
    public void save(Location location) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(location);
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(long locationId) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Location location = session.get(Location.class,locationId);
            session.remove(location);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Location findByCoordinates(Double latitude, Double longitude) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Location l" +
                    " WHERE l.latitude = :latitude AND l.longitude = :longitude";
            Query query = session.createQuery(hql);
            query.setParameter("latitude",latitude);
            query.setParameter("longitude",longitude);
            return (Location) query.getSingleResult();
        }
    }

    public List<Location> findByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Location l JOIN l.user u" +
                    " WHERE u.login = :login";
            List<Location> location = session.createQuery(hql, Location.class)
                    .setParameter("login", login)
                    .getResultList();
            return location;
        }
    }

    public Location findById(long locationId) throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Location.class,locationId);
        }
    }
}
