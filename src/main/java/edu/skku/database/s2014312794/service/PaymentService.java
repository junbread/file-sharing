package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;
import edu.skku.database.s2014312794.model.Bill;
import edu.skku.database.s2014312794.model.User;

import java.util.Optional;

public class PaymentService {
    public static Optional<Bill> getCurrentBill(User user) {
        String sql = "SELECT * FROM bills WHERE user_id = :id ORDER BY issue_date DESC LIMIT 1";

        return DataSource.getConnection().withHandle(handle ->
                handle.createQuery(sql)
                        .bindBean(user)
                        .mapToBean(Bill.class)
                        .findOne());
    }

    public static void doPayment(User user) {
        String sql = "UPDATE bills SET status = 'FINISHED' WHERE user_id = :id AND status = 'PENDING'";
        DataSource.getConnection().useHandle(handle -> handle.createUpdate(sql).bindBean(user).execute());
    }
}
