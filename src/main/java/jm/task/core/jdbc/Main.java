package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        UserDao userDaoJDBC = new UserDaoJDBCImpl();

        try {
            userDaoJDBC.createUsersTable();
            userDaoJDBC.saveUser("Ivan", "Ivanov", (byte) 21);
            userDaoJDBC.saveUser("Sergey", "Parshivluk", (byte) 32);
            userDaoJDBC.saveUser("Sergey", "Sergeyenko", (byte) 19);
            userDaoJDBC.saveUser("Zidane", "NeBeyGolovoy", (byte) 34);
            System.out.println(userDaoJDBC.getAllUsers().toString());
            userDaoJDBC.cleanUsersTable();
            userDaoJDBC.dropUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
