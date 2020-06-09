package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.Role;
import edu.skku.database.s2014312794.model.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public static boolean isValidUser(String id, String pw) {
        String sql = "SELECT IF((SELECT pw FROM users WHERE id = :id) = SHA1(:pw), true, false)";

        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .bind("pw", pw)
                .mapTo(Boolean.class)
                .one());
    }

    public static User getUser(String id) {
        String sql = "SELECT * FROM users WHERE id = :id";

        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .bind("id", id)
                .map(new UserMapper())
                .one());
    }

    public static void updateUser(String id) {
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
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setAddress(rs.getString("address"));
            user.setPhone(rs.getString("phone"));
            user.setBirthday(rs.getDate("birthday"));
            user.setJoinDate(rs.getDate("join_date"));

            return user;
        }
    }
}
