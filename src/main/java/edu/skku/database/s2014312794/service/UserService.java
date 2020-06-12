package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.User;

public class UserService {
    public static User getUser(String id) {
        String sql = "SELECT * FROM users WHERE id = :id";

        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .mapToBean(User.class)
                .one());
    }

    public static void updateUser(String id, User user) {
        String sql = "UPDATE users SET name = :name, account_number = :accountNumber, address = :address, phone = :phone, birthday = :birthday, subscription = :subscription WHERE id = :id";

        DataSource.getConnection().useHandle(handle -> handle.createUpdate(sql)
                .bindBean(user)
                .bind("id", id)
                .execute());
    }

    public static void deleteUser(String id) {
        String sql = "DELETE FROM user WHERE id = :id";

        DataSource.getConnection().withHandle(handle -> handle.createUpdate(sql)
                .bind("id", id)
                .execute());
    }
}
