package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Util {

    private static final String URL_BASE = "jdbc:mysql://localhost:3306/user_base";
    private static final String USER_NAME_CONNECT_BASE = "root";
    private static final String PASSWORD_CONNECT_BASE = "12qq1";

    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static Connection getConnection() {

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL_BASE, USER_NAME_CONNECT_BASE, PASSWORD_CONNECT_BASE);
            System.out.println("Connecting to base successful");
        } catch (SQLException e) {
            System.out.println("Connecting Error... Exception: " + e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {

        Configuration configuration = new Configuration();

        Properties dbSettings = new Properties();
        dbSettings.put(Environment.URL, URL_BASE);
        dbSettings.put(Environment.USER, USER_NAME_CONNECT_BASE);
        dbSettings.put(Environment.PASS, PASSWORD_CONNECT_BASE);
        dbSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

        configuration.setProperties(dbSettings);
        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        System.out.println("Connection...");

        return sessionFactory;
    }
}
