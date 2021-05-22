package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import org.hibernate.HibernateException;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        UserDao userDaoHibernate = new UserDaoHibernateImpl();

        try {

            userDaoHibernate.createUsersTable();

            userDaoHibernate.saveUser("Vakhit", "Sharipov", (byte) 27);
            userDaoHibernate.saveUser("Andrey", "Vudrya", (byte) 33);
            userDaoHibernate.saveUser("Sergey", "Ivanov", (byte) 29);
            userDaoHibernate.saveUser("Ivan", "Sergeev", (byte) 77);

            System.out.println(userDaoHibernate.getAllUsers().toString());

            userDaoHibernate.cleanUsersTable();

            userDaoHibernate.dropUsersTable();

        } catch (HibernateException | SQLException e) {
            e.printStackTrace();
        }
    }
}
