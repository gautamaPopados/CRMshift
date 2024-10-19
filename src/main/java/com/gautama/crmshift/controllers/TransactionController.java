package com.gautama.crmshift.controllers;

import com.gautama.crmshift.dto.TransactionDTO;
import com.gautama.crmshift.entities.Transaction;
import com.gautama.crmshift.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction Controller")
@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Получить список всех транзакций")
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @Operation(summary = "Получить информацию о конкретной транзакции")
    @GetMapping("/transactions/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.findTransactionById(id);
    }

    @Operation(summary = "Добавить транзакцию")
    @PostMapping("/sellers/{sellerId}/transactions")
    public void createTransaction(@PathVariable Long sellerId ,@RequestBody TransactionDTO transactionDTO) {
        transactionService.createTransaction(transactionDTO, sellerId);
    }

    @Operation(summary = "Получить все транзакции конкретного продавца")
    @GetMapping("/seller/{sellerId}/transactions")
    public List<Transaction> getTransactionsBySeller(@PathVariable Long sellerId) {
        return transactionService.getTransactionsBySeller(sellerId);
    }
}
