package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String SQL_COMMAND_CREATE_BASE = "CREATE TABLE IF NOT EXISTS usersTable" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(30)," +
            "lastName VARCHAR(30)," +
            "age TINYINT)";

    private static final String SQL_COMMAND_DROP_TABLE = "DROP TABLE IF EXISTS usersTable";


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() throws HibernateException {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(SQL_COMMAND_CREATE_BASE).executeUpdate();
                session.beginTransaction().commit();
                System.out.println("Таблица 'userstable' успешно создана...");
            } catch (HibernateException e) {
                e.printStackTrace();
                session.beginTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() throws HibernateException {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(SQL_COMMAND_DROP_TABLE).executeUpdate();
                session.beginTransaction().commit();
                System.out.println("Таблица 'userstable' успешно удалена...");
            } catch (HibernateException e) {
                e.printStackTrace();
                session.beginTransaction().rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                User user = new User(name, lastName, age);
                session.beginTransaction();
                session.save(user);
                session.beginTransaction().commit();
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            } catch (HibernateException e) {
                e.printStackTrace();
                session.beginTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                User userRemove = session.get(User.class, id);
                session.delete(userRemove);
                session.beginTransaction().commit();
                System.out.println("User " + userRemove.getName() + " удалён");
            } catch (HibernateException e) {
                e.printStackTrace();
                session.beginTransaction().rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                List<User> userList = session.createQuery("from User")
                        .getResultList();
                session.beginTransaction().commit();
                return userList;
            } catch (HibernateException e) {
                e.printStackTrace();
                session.beginTransaction().rollback();
            }
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createQuery("delete User ").executeUpdate();
                session.beginTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.beginTransaction().rollback();
            }
        }
    }
}
