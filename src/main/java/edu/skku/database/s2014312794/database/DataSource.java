package edu.skku.database.s2014312794.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.*;

public class DataSource {

    private static Jdbi connection;

    static {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/project?serverTimezone=UTC");
        ds.setUsername("root");

        connection = Jdbi.create(ds);
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

        try {
            Class.forName("org.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", null);
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
            } catch (SQLException ignored) {}
        }
    }
}
