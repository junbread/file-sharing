package edu.skku.database.s2014312794.model;

import java.util.ArrayList;
import java.util.List;

public class UserStat {
    String id;
    String name;
    Long downloadCnt;

    List<ItemStat> itemStats = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDownloadCnt() {
        return downloadCnt;
    }

    public void setDownloadCnt(Long downloadCnt) {
        this.downloadCnt = downloadCnt;
    }

    public List<ItemStat> getItemStats() {
        return itemStats;
    }

    public void setItemStats(List<ItemStat> itemStats) {
        this.itemStats = itemStats;
    }
}
