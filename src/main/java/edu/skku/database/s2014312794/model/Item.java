package edu.skku.database.s2014312794.model;

import java.util.Date;

public class Item {
    private Integer id;
    private User author;
    private ItemType type;
    private String name;
    private Category category;
    private Integer size;
    private String url;
    private Date updateTime;
    private ItemHardware hardware;
    private ItemOs os;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ItemHardware getHardware() {
        return hardware;
    }

    public void setHardware(ItemHardware hardware) {
        this.hardware = hardware;
    }

    public ItemOs getOs() {
        return os;
    }

    public void setOs(ItemOs os) {
        this.os = os;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
