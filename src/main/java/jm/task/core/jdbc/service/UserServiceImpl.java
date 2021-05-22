package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final String SQL_COMMAND_CREATE_BASE = "CREATE TABLE IF NOT EXISTS usersTable" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(30)," +
            "lastName VARCHAR(30)," +
            "age TINYINT)";

    private static final String SQL_COMMAND_DROP_TABLE = "DROP TABLE IF EXISTS usersTable";

    Transaction transaction;
    Session session;

    @Override
    public void createUsersTable() throws HibernateException {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_COMMAND_CREATE_BASE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица 'userstable' успешно создана...");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() throws HibernateException {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_COMMAND_DROP_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица 'userstable' успешно удалена...");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User userRemove = session.get(User.class, id);
            session.delete(userRemove);
            transaction.commit();
            System.out.println("User " + userRemove.getName() + " удалён");
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<User> userList = session.createQuery("from User")
                    .getResultList();
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
