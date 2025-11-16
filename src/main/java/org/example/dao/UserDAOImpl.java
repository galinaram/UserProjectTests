package org.example.dao;

import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public User save(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            User user = (User) session.get(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}