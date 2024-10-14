package com.gautama.crmshift.services;

import com.gautama.crmshift.entities.Transaction;
import com.gautama.crmshift.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createSeller(Transaction transaction) {
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAllSellers() {
        return transactionRepository.findAll();  // Извлекаем всех продавцов из БД
    }
}
