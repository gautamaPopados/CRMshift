package com.gautama.crmshift.services;

import com.gautama.crmshift.dto.TransactionDTO;
import com.gautama.crmshift.entities.Transaction;
import com.gautama.crmshift.exceptions.ResourceNotFoundException;
import com.gautama.crmshift.repositories.SellerRepository;
import com.gautama.crmshift.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SellerRepository sellerRepository;

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Transaction with id " + id + " not found"));
    }

    public void createTransaction(TransactionDTO transactionDTO, Long sellerId) {

        Transaction transaction = sellerRepository.findById(sellerId).map(seller -> {
            Transaction newTransaction = new Transaction(
                    transactionDTO.getPaymentType(),
                    transactionDTO.getAmount(),
                    seller
            );
            newTransaction.setTransactionDate(LocalDateTime.now());
            return transactionRepository.save(newTransaction);
        }).orElseThrow(() -> new ResourceNotFoundException("Seller with id " + sellerId + " not found"));
    }

    public List<Transaction> getTransactionsBySeller(Long sellerId) {
        return transactionRepository.findTransactionsBySeller(sellerId);
    }
}
