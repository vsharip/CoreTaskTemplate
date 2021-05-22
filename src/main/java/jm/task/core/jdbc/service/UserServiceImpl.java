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

    Connection connection;
    Statement statement;

    private static final String SQL_COMMAND_CREATE_BASE = "CREATE TABLE IF NOT EXISTS usersTable" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(30)," +
            "lastName VARCHAR(30)," +
            "age TINYINT)";

    private static final String SQL_COMMAND_DROP_TABLE = "DROP TABLE usersTable";

    public void createUsersTable() throws SQLException {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(SQL_COMMAND_CREATE_BASE);
            System.out.println("Table \"usersTable\" create successful");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void dropUsersTable() throws SQLException {

        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "usersTable", null); //t is your tableName (defined in loop of your question)

            if (rs.isBeforeFirst()) {
                statement.executeUpdate(SQL_COMMAND_DROP_TABLE);
                System.out.println("Table \"usersTable\" DROP was successful!");
            } else {
                System.out.println("Таблицы \"usersTable\" не существует!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            int rows = statement.executeUpdate(
                    "INSERT usersTable(name, lastName, age) VALUES ("
                            + "'" + name + "', " + "'" + lastName + "', " + age + ")");

            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void removeUserById(long id) throws SQLException {

        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            if (id < 1) {
                System.out.println("ID cannot be negative");
            } else {
                int rows = statement.executeUpdate("DELETE FROM usersTable WHERE Id = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        ResultSet rs;
        List<User> userList = new ArrayList<>();
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM usersTable");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                userList.add(user);
            }

            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null;
    }

    public void cleanUsersTable() throws SQLException {

        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate("TRUNCATE TABLE usersTable");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
