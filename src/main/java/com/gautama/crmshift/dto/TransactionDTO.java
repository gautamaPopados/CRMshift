package com.gautama.crmshift.dto;

import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.enums.PaymentType;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class TransactionDTO implements Serializable {

    private BigDecimal amount;
    private PaymentType paymentType;

    public TransactionDTO() {
    }

    public TransactionDTO( BigDecimal amount, PaymentType paymentType) {
        this.amount = amount;
        this.paymentType = paymentType;
    }
}