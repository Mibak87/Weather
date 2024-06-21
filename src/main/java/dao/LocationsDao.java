package dao;

import model.Location;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class LocationsDao {
    public void saveOrUpdate(Location location) throws HibernateException {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(location);
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
            String hql = "FROM Location l JOIN l.users u" +
                    " WHERE u.login = :login";
            List<Location> location = session.createQuery(hql, Location.class)
                    .setParameter("login", login)
                    .getResultList();
            return location;
        }
    }

    public List<Location> findAll() throws HibernateException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Location", Location.class).getResultList();
        }
    }
}
