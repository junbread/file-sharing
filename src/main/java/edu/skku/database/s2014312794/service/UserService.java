package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.UserRole;
import edu.skku.database.s2014312794.model.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserService {
    public static User getUser(String id) {
        String sql = "SELECT * FROM users WHERE id = :id";

        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .map(new UserMapper())
                .one());
    }

    public static void updateUser(String id, User user) {
        return;
    }

    public static void deleteUser(String id) {
        String sql = "DELETE FROM user WHERE id = :id";

        DataSource.getConnection().withHandle(handle -> handle.createUpdate(sql)
                .bind("id", id)
                .execute());
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User map(ResultSet rs, StatementContext ctx) throws SQLException {
            User user = new User();

            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setRole(UserRole.valueOf(rs.getString("role")));
            user.setAddress(rs.getString("address"));
            user.setPhone(rs.getString("phone"));
            user.setBirthday(rs.getObject("birthday", LocalDate.class));
            user.setJoinDate(rs.getObject("join_date", LocalDate.class));
            user.setSubscription(rs.getBoolean("subscription"));

            return user;
        }
    }
}
