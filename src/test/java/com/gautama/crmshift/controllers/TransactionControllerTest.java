package com.gautama.crmshift.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gautama.crmshift.dto.TransactionDTO;
import com.gautama.crmshift.entities.Transaction;
import com.gautama.crmshift.enums.PaymentType;
import com.gautama.crmshift.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void TransactionController_getAllTransactions_ShouldReturnListOfTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionService.findAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void TransactionController_getTransactionById_ShouldReturnTransaction() throws Exception {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionService.findTransactionById(transactionId)).thenReturn(transaction);

        mockMvc.perform(get("/api/transactions/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void TransactionController_createTransaction_ShouldCreateTransaction() throws Exception {
        Long sellerId = 1L;
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(BigDecimal.valueOf(100));
        transactionDTO.setPaymentType(PaymentType.valueOf("CARD"));

        mockMvc.perform(post("/api/sellers/{sellerId}/transactions", sellerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void TransactionController_getTransactionsBySeller_ShouldReturnListOfTransactions() throws Exception {
        Long sellerId = 1L;
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(transactionService.getTransactionsBySeller(sellerId)).thenReturn(transactions);

        mockMvc.perform(get("/api/seller/{sellerId}/transactions", sellerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))); // Adjust according to expected size
    }
}
