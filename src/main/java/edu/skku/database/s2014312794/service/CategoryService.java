package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.Category;

import java.util.List;

public class CategoryService {

    public static List<Category> getCategories() {
        String sql = "SELECT * FROM categories";
        return DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                .mapToBean(Category.class)
                .list());
    }
}
