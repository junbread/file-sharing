package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.DownloadLog;
import edu.skku.database.s2014312794.model.Item;
import edu.skku.database.s2014312794.model.ItemStat;
import edu.skku.database.s2014312794.model.UserStat;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.result.RowView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatService {

    public static List<DownloadLog> getDownloadLogs(Item item) {
        String sql = "SELECT * FROM downloads_ext WHERE item_id = :id";

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sql)
                        .bindBean(item)
                        .mapToBean(DownloadLog.class)
                        .list());
    }

    public static List<UserStat> getProviderStats(boolean asc, int threshold) {
        String sql = "SELECT * FROM providers_stat ORDER BY user_download_cnt " + (asc ? "ASC" : "DESC") + " LIMIT :threshold";

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("threshold", threshold)
                        .registerRowMapper(BeanMapper.factory(UserStat.class, "user"))
                        .registerRowMapper(BeanMapper.factory(ItemStat.class, "item"))
                        .reduceRows((Map<String, UserStat> map, RowView rowView) -> {
                            UserStat stat = map.computeIfAbsent(rowView.getColumn("user_id", String.class),
                                    id -> rowView.getRow(UserStat.class));

                            if (rowView.getColumn("item_id", Integer.class) != null) {
                                stat.getItemStats().add(rowView.getRow(ItemStat.class));
                            }
                        })
                        .collect(Collectors.toList()));
    }

    public static List<UserStat> getSubscriberStats(boolean asc, int threshold) {
        String sql = "SELECT * FROM subscribers_stat ORDER BY user_download_cnt " + (asc ? "ASC" : "DESC") + " LIMIT :threshold";

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("threshold", threshold)
                        .registerRowMapper(BeanMapper.factory(UserStat.class, "user"))
                        .registerRowMapper(BeanMapper.factory(ItemStat.class, "item"))
                        .reduceRows((Map<String, UserStat> map, RowView rowView) -> {
                            UserStat stat = map.computeIfAbsent(rowView.getColumn("user_id", String.class),
                                    id -> rowView.getRow(UserStat.class));

                            if (rowView.getColumn("item_id", Integer.class) != null) {
                                stat.getItemStats().add(rowView.getRow(ItemStat.class));
                            }
                        })
                        .collect(Collectors.toList()));
    }

    public static List<ItemStat> getItemStats(boolean asc, int threshold) {
        String sql = "SELECT * FROM items_ext ORDER BY download_cnt " + (asc ? "ASC" : "DESC") + " LIMIT :threshold";

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("threshold", threshold)
                        .mapToBean(ItemStat.class)
                        .list());
    }
}
