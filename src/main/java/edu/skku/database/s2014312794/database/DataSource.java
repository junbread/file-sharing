package edu.skku.database.s2014312794.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataSource {

    private static Jdbi connection;

    static {
        try (InputStream in = DataSource.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties dbProp = new Properties();
            dbProp.load(in);

            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(dbProp.getProperty("database.driverClassName"));
            ds.setUrl(dbProp.getProperty("database.url"));
            ds.setUsername(dbProp.getProperty("database.username"));
            ds.setPassword(dbProp.getProperty("database.password"));

            connection = Jdbi.create(ds);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot connect to database");
        }
    }

    public static Jdbi getConnection() {
        return connection;
    }

    /**
     * Prove that I can connect to database using pure JDBC
     */
    public static boolean testConnection() {
        Connection conn = null;
        Statement stmt = null;

        try (InputStream in = DataSource.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties dbProp = new Properties();
            dbProp.load(in);

            Class.forName(dbProp.getProperty("database.driverClassName"));
            conn = DriverManager.getConnection(
                    dbProp.getProperty("database.url"),
                    dbProp.getProperty("database.username"),
                    dbProp.getProperty("database.password")
            );

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT 1=1");

            if (rs.next())
                return rs.getBoolean(1);

            return false;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
