package edu.skku.database.s2014312794.model;

import java.time.LocalDate;

public class Payment {
    private Integer id;
    private User user;
    private PaymentType type;
    private Long amount;
    private PaymentStatus status;
    private LocalDate dueDate;

    public Payment() {
    }

    public Payment(Integer id, User user, PaymentType type, Long amount, PaymentStatus status, LocalDate dueDate) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
