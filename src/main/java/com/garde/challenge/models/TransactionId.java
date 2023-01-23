package com.garde.challenge.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransactionId implements Serializable {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private TransactionType type;

    public TransactionId() {}

    public TransactionId(LocalDate date, TransactionType type) {
        this.date = date;
        this.type = type;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public TransactionType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TransactionId)) {
            return false;
        }
        TransactionId transactionId = (TransactionId) o;
        return Objects.equals(date, transactionId.date) && Objects.equals(type, transactionId.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type);
    }
}
