package edu.skku.database.s2014312794.model;

import java.time.LocalDateTime;

public class DownloadLog {
    private String userId;
    private String userName;
    private Integer itemId;
    private String itemName;
    private LocalDateTime downloadTime;

    public DownloadLog() {
    }

    public DownloadLog(String userId, String userName, Integer itemId, String itemName, LocalDateTime downloadTime) {
        this.userId = userId;
        this.userName = userName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.downloadTime = downloadTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDateTime getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(LocalDateTime downloadTime) {
        this.downloadTime = downloadTime;
    }
}
