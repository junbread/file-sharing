package edu.skku.database.s2014312794.model;

public class ItemStat {
    int id;
    String name;
    boolean deleted;
    Long downloadCnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getDownloadCnt() {
        return downloadCnt;
    }

    public void setDownloadCnt(Long downloadCnt) {
        this.downloadCnt = downloadCnt;
    }
}
