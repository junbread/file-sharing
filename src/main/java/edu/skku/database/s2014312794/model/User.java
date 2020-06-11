package edu.skku.database.s2014312794.model;

import java.time.LocalDate;

public class User {
    private String id;
    private String name;
    private UserRole role;
    private String accountNumber;
    private String address;
    private String phone;
    private LocalDate birthday;
    private LocalDate joinDate;
    private Boolean subscription;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, String name, UserRole role, String accountNumber, String address, String phone, LocalDate birthday, LocalDate joinDate, Boolean subscription) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.accountNumber = accountNumber;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.joinDate = joinDate;
        this.subscription = subscription;
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }
}
