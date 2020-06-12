package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.*;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ItemService {
    public static Item getItem(int id) {
        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery("SELECT * FROM items_ext WHERE id = :id")
                        .bind("id", id)
                        .map(new ItemMapper())
                        .one());
    }

    public static List<Item> getItems(Map<String, Object> criteria) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM items_ext WHERE 1=1");
        criteria.forEach((key, value) -> {
            if (key.equals("name"))
                sqlBuilder.append(" AND " + key + " LIKE CONCAT('%',:" + key + ",'%')");
            else
                sqlBuilder.append(" AND " + key + " = :" + key);
        });
        sqlBuilder.append(" AND deleted = false");

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sqlBuilder.toString())
                        .bindMap(criteria)
                        .map(new ItemMapper())
                        .list());
    }

    public static List<Item> getDependencies(int itemId) {
        String sql = "SELECT * FROM dependencies_ext WHERE item_id = :item_id";

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("item_id", itemId)
                        .map((rs, ctx) -> new Item(
                                rs.getInt("require_item_id"),
                                null,
                                null,
                                rs.getString("require_item_name"),
                                null,
                                rs.getInt("require_item_size"),
                                rs.getString("require_item_url"),
                                null,
                                ItemHardware.valueOf(rs.getString("require_item_hardware")),
                                ItemOs.valueOf(rs.getString("require_item_os")),
                                null))
                        .list()
        );
    }

    public static void insertDownloadLog(User user, Item item) {
        String sql = "INSERT INTO downloads (user_id, item_id) VALUES (:user_id, :item_id)";

        DataSource.getConnection().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("user_id", user.getId())
                        .bind("item_id", item.getId())
                        .execute());
    }

    public static void insertItem(Item item, List<Item> dependencies) {
        String insertSql = "INSERT INTO items" +
                " (author_id, type, name, category_id, size, url, hardware, os, description)" +
                " VALUES(:author.id, :type, :name, :category.id, :size, :url, :hardware, :os, :description)";

        String dependencySql = "INSERT INTO dependencies (item_id, require_item_id) values (:item_id, :require_item_id)";

        DataSource.getConnection().useTransaction(handle -> {
            int id = handle.createUpdate(insertSql).bindBean(item)
                    .executeAndReturnGeneratedKeys()
                    .mapTo(Integer.class)
                    .one();
            if (!dependencies.isEmpty()) {
                PreparedBatch b = handle.prepareBatch(dependencySql);
                dependencies.forEach(d -> b
                        .bind("item_id", id)
                        .bind("require_item_id", d.getId())
                        .add(dependencySql)
                );
                b.execute();
            }
        });
    }

    public static void updateItem(int id, Item item) {
        String sql = "UPDATE items SET" +
                " name = :name, category_id = :category.id, size = :size, hardware = :hardware," +
                " os = :os, description = :description" +
                " WHERE id = :id";

        DataSource.getConnection().useHandle(handle ->
                handle.createUpdate(sql)
                        .bindBean(item)
                        .bind("id", id)
                        .execute());
    }

    public static void deleteItem(int id) {
        String sql = "UPDATE items SET deleted = true WHERE id=:id";

        DataSource.getConnection().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .execute());
    }

    private static class ItemMapper implements RowMapper<Item> {

        @Override
        public Item map(ResultSet rs, StatementContext ctx) throws SQLException {
            Item item = new Item();

            // Direct Fields
            item.setId(rs.getInt("id"));
            item.setType(ItemType.valueOf(rs.getString("type")));
            item.setName(rs.getString("name"));
            item.setSize(rs.getInt("size"));
            item.setUrl(rs.getString("url"));
            item.setUpdateTime(rs.getDate("update_time"));
            item.setHardware(ItemHardware.valueOf(rs.getString("hardware")));
            item.setOs(ItemOs.valueOf(rs.getString("os")));
            item.setDescription(rs.getString("description"));

            // Nested Fields (Only contains subset)
            item.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            item.setAuthor(new User(rs.getString("author_id"), rs.getString("author_name")));

            return item;
        }
    }
}
