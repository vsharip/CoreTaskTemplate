package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static final String URL_BASE = "jdbc:mysql://localhost:3306/user_base";
    private static final String USER_NAME_CONNECT_BASE = "root";
    private static final String PASSWORD_CONNECT_BASE = "12qq1";

    public static Connection getConnection() {

        Connection connection = null;

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

    public static class HibernateUtil {

        private static StandardServiceRegistry standardServiceRegistry;
        private static SessionFactory sessionFactory;

        static {
            StandardServiceRegistryBuilder regBuilder = new StandardServiceRegistryBuilder();

            Map<String, String> dbSettings = new HashMap<>();
            dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/user_base");
            dbSettings.put(Environment.USER, "root");
            dbSettings.put(Environment.PASS, "12qq1");
            dbSettings.put(Environment.DRIVER, "com.mysql.JDBC.Driver");
            dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

            regBuilder.applySettings(dbSettings);
            standardServiceRegistry = regBuilder.build();

            MetadataSources mdSource = new MetadataSources(standardServiceRegistry);
            Metadata metadata = mdSource.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }

        public static SessionFactory getSessionFactory() {
            System.out.println("Соединенеие с базой выполнено успешно!");
            return sessionFactory;
        }
    }

    // реализуйте настройку соеденения с БД
}
