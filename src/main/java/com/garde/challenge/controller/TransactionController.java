package com.garde.challenge.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.garde.challenge.models.Transaction;
import com.garde.challenge.models.TransactionId;
import com.garde.challenge.models.TransactionType;
import com.garde.challenge.service.TransactionService;

@RestController
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Transaction> saveTransaction(@RequestBody List<Transaction> transactionList) {
        List<Transaction> created = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            Optional<Transaction> trx = transactionService.findTransactionById(new TransactionId(transaction.getDate(), transaction.getType()));
            if (trx.isPresent()) {
                logger.info("# ============ Duplicate transaction found for date: {}, type: {}. Combining amounts {} and {}. ============",
                    trx.get().getDate(),
                    trx.get().getType(),
                    trx.get().getAmount(),
                    transaction.getAmount()
                );
                transaction.addAmount(trx.get().getAmount());
            }
            created.add(transactionService.saveTransaction(transaction));
        }
        return created;
    }

    @GetMapping("/transactions/{date}/{type}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Transaction> getTransaction(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date, @PathVariable TransactionType type) {
        TransactionId id = new TransactionId(date, type);
        Optional<Transaction> returned = transactionService.findTransactionById(id);
        return returned;
    }

    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Transaction> getAll() {
        return transactionService.findAll();
    }
}
