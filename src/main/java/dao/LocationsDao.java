package dao;

import model.Location;
import model.User;
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

    public void delete(Double latitude,Double longitude, User user) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Location" +
                    " WHERE latitude = :latitude AND longitude = :longitude AND user = :user";
            Query query = session.createQuery(hql);
            query.setParameter("latitude",latitude);
            query.setParameter("longitude",longitude);
            query.setParameter("user",user);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
}
