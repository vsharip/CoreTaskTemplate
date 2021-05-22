package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import org.hibernate.HibernateException;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        UserDao userDaoJDBC = new UserDaoJDBCImpl();

        try {

            userDaoJDBC.createUsersTable();

            userDaoJDBC.saveUser("Vakhit", "Sharipov", (byte) 27);
            userDaoJDBC.saveUser("Andrey", "Vudrya", (byte) 33);
            userDaoJDBC.saveUser("Sergey", "Ivanov", (byte) 29);
            userDaoJDBC.saveUser("Ivan", "Sergeev", (byte) 77);

            System.out.println(userDaoJDBC.getAllUsers().toString());

            userDaoJDBC.cleanUsersTable();

            userDaoJDBC.dropUsersTable();

        } catch (HibernateException | SQLException e) {
            e.printStackTrace();
        }
    }
}
