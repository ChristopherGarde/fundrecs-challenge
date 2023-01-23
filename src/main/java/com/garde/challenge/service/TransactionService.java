package com.garde.challenge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.garde.challenge.models.Transaction;
import com.garde.challenge.models.TransactionId;
import com.garde.challenge.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Optional<Transaction> findTransactionById(TransactionId id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }
}
