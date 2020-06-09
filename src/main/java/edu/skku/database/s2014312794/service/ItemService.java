package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.Item;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemService {

    public static List<Item> getItems() {
        String sql = "SELECT * FROM items";

        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .map(new ItemMapper())
                .list());
    }

    private static class ItemMapper implements RowMapper<Item> {

        @Override
        public Item map(ResultSet rs, StatementContext ctx) throws SQLException {
            Item item = new Item();

            // TODO implement mapping function

            return item;
        }
    }

}
