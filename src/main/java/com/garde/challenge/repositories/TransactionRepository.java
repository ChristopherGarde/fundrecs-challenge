package com.garde.challenge.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garde.challenge.models.Transaction;
import com.garde.challenge.models.TransactionId;

public interface TransactionRepository extends JpaRepository<Transaction, TransactionId> {
    Optional<Transaction> findById(TransactionId transactionId);
}
