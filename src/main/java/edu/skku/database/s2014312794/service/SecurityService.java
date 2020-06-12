package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.User;

public class SecurityService {

    public static void register(User user, String pw) {
        String sql = "INSERT INTO users" +
                " (id, name, role, address, phone, birthday, subscription, account_number, pw)" +
                " VALUES (:id, :name, :role, :address, :phone, :birthday, :subscription, :accountNumber, SHA1(:pw))";

        DataSource.getConnection().useHandle(handle -> handle.createUpdate(sql)
                .bindBean(user)
                .bind("pw", pw)
                .execute());
    }

    public static boolean isDuplicated(String id) {
        String sql = "SELECT (SELECT id FROM users WHERE id = :id) IS NOT NULL";

        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .mapTo(Boolean.class)
                .one());
    }

    public static boolean login(String id, String pw) {
        String sql1 = "SELECT IFNULL((SELECT pw FROM users WHERE id = :id) = SHA1(:pw), false)";
        Boolean isValidUser = DataSource.getConnection().withHandle(handle -> handle.createQuery(sql1)
                .bind("id", id)
                .bind("pw", pw)
                .mapTo(Boolean.class)
                .one());

        if (!isValidUser)
            return false;

        // update login time
        String sql2 = "UPDATE users SET last_login_time = CURRENT_TIMESTAMP() WHERE id = :id";
        DataSource.getConnection().useHandle(handle -> handle.createUpdate(sql2)
                .bind("id", id)
                .execute());

        return true;
    }
}
