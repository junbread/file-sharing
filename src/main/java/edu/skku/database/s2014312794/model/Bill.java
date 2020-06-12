package edu.skku.database.s2014312794.model;

import java.time.LocalDate;

public class Bill {
    LocalDate issueDate;
    Long income;
    Long payment;
    BillStatus status;

    public Bill() {
    }

    public Bill(LocalDate issueDate, Long income, Long payment, BillStatus status) {
        this.issueDate = issueDate;
        this.income = income;
        this.payment = payment;
        this.status = status;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public Long getBalance() {
        return income - payment;
    }
}
