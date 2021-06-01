package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    private static final String SQL_COMMAND_CREATE_BASE = "CREATE TABLE IF NOT EXISTS usersTable" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(30)," +
            "lastName VARCHAR(30)," +
            "age TINYINT)";

    private static final String SQL_COMMAND_DROP_TABLE = "DROP TABLE usersTable";

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() throws SQLException {

        connection.setAutoCommit(false);
        try {
            connection.createStatement().executeUpdate(SQL_COMMAND_CREATE_BASE);
            connection.commit();
            connection.createStatement().close();
            System.out.println("Table \"usersTable\" create successful");
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {

        connection.setAutoCommit(false);
        try {

            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "usersTable", null); //it is your tableName (defined in loop of your question)

            if (rs.isBeforeFirst()) {
                connection.createStatement().executeUpdate(SQL_COMMAND_DROP_TABLE);
                connection.commit();
                rs.close();
                connection.createStatement().close();
                System.out.println("Table \"usersTable\" DROP was successful!");
            } else {
                System.out.println("Таблицы \"usersTable\" не существует!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {

        connection.setAutoCommit(false);
        try {
            connection.createStatement().executeUpdate(
                    "INSERT usersTable(name, lastName, age) VALUES ("
                            + "'" + name + "', " + "'" + lastName + "', " + age + ")");
            connection.commit();
            connection.createStatement().close();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {

        connection.setAutoCommit(false);
        try {
            if (id < 1) {
                System.out.println("ID cannot be negative");
            } else {
                connection.createStatement().executeUpdate("DELETE FROM usersTable WHERE Id = " + id);
                connection.commit();
                connection.createStatement().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {

        ResultSet rs;
        List<User> userList = new ArrayList<>();

        connection.setAutoCommit(false);

        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM usersTable");
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
            connection.commit();
            rs.close();
            connection.createStatement().close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
       connection.setAutoCommit(true);
        }
        return null;
    }

    @Override
    public void cleanUsersTable() throws SQLException {

        connection.setAutoCommit(false);
        try {
            connection.createStatement().executeUpdate("TRUNCATE TABLE usersTable");
            connection.commit();
            connection.createStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
