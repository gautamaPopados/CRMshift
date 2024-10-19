package com.gautama.crmshift.dto;

import com.gautama.crmshift.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
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