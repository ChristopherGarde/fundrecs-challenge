package com.garde.challenge.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(TransactionId.class)
public class Transaction {
    @Id
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name="trxdate")
    private LocalDate date;

    @Id
    @Column(name="trxtype")
    private TransactionType type;

    private double amount;

    public Transaction() {}
    public Transaction(LocalDate date, TransactionType type, double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public TransactionType getType() {
        return this.type;
    }

    public double getAmount() {
        return this.amount;
    }

    public void addAmount(double toAdd) {
        this.amount += toAdd;
    }
}
