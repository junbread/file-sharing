package edu.skku.database.s2014312794.service;

import edu.skku.database.s2014312794.database.DataSource;

import java.util.List;
import java.util.Map;


public class PaymentService {

    private static Long joiningFee;
    private static Long subscriptionFee;
    private static Long downloadRate;
    private static Long uploadRate;

    static {
        String sql = "SELECT * FROM fees";
        List<Map<String, Object>> results =
                DataSource.getConnection().withHandle(handle -> handle.createQuery(sql)
                        .mapToMap()
                        .list());

        for (Map<String, Object> m : results) {
            if (m.containsValue("joining"))
                joiningFee = (Long) m.get("value");
            if (m.containsValue("subscription"))
                subscriptionFee = (Long) m.get("value");
            if (m.containsValue("download_rate"))
                downloadRate = (Long) m.get("value");
            if (m.containsValue("upload_rate"))
                uploadRate = (Long) m.get("value");
        }
    }
}
