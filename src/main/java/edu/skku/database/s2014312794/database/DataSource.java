package edu.skku.database.s2014312794.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;

public class DataSource {

    private static Jdbi connection;

    static {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setUrl("jdbc:mariadb://localhost:3306/project");
        ds.setUsername("root");

        connection = Jdbi.create(ds);
    }

    public static Jdbi getConnection() {
        return connection;
    }
}
