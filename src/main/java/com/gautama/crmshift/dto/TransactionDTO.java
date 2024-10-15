package com.gautama.crmshift.dto;

import com.gautama.crmshift.entities.Seller;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class TransactionDTO implements Serializable {

    private BigDecimal amount;
    private String paymentType;

    public TransactionDTO() {
    }

    public TransactionDTO( BigDecimal amount, String paymentType) {
        this.amount = amount;
        this.paymentType = paymentType;
    }
}