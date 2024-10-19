package com.gautama.crmshift.services;

import com.gautama.crmshift.dto.TransactionDTO;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.entities.Transaction;
import com.gautama.crmshift.enums.PaymentType;
import com.gautama.crmshift.exceptions.ResourceNotFoundException;
import com.gautama.crmshift.repositories.SellerRepository;
import com.gautama.crmshift.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private Seller seller;
    private TransactionDTO transactionDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller("John Doe", "john@example.com");
        seller.setId(1L);

        transaction = new Transaction(PaymentType.CARD, BigDecimal.valueOf(1000), seller);
        transaction.setId(1L);

        transactionDTO = new TransactionDTO(BigDecimal.valueOf(1000),PaymentType.CARD);
    }

    @Test
    public void TransactionService_findAllTransactions_ShouldReturnTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.findAllTransactions();

        assertEquals(1, result.size());
        assertEquals(transaction.getId(), result.get(0).getId());
    }

    @Test
    public void TransactionService_findTransactionById_ShouldReturnTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.findTransactionById(1L);

        assertEquals(transaction.getId(), result.getId());
    }

    @Test
    public void TransactionService_findTransactionById_ShouldThrowException_WhenNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> transactionService.findTransactionById(1L));

        assertEquals("Transaction with id 1 not found", exception.getMessage());
    }

    @Test
    public void TransactionService_createTransaction_ShouldCreateTransaction() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.createTransaction(transactionDTO, 1L);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void TransactionService_createTransaction_ShouldThrowException_WhenSellerNotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> transactionService.createTransaction(transactionDTO, 1L));

        assertEquals("Seller with id 1 not found", exception.getMessage());
    }

    @Test
    public void TransactionService_getTransactionsBySeller_ShouldReturnTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        when(transactionRepository.findTransactionsBySeller(1L)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactionsBySeller(1L);

        assertEquals(1, result.size());
        assertEquals(transaction.getId(), result.get(0).getId());
    }

    @Test
    public void TransactionService_getTransactionsBySeller_ShouldReturnEmptyList_WhenNoTransactions() {
        when(transactionRepository.findTransactionsBySeller(1L)).thenReturn(new ArrayList<>());

        List<Transaction> result = transactionService.getTransactionsBySeller(1L);

        assertEquals(0, result.size());
    }
}
