package com.gautama.crmshift.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gautama.crmshift.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "seller_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seller seller;

    public Transaction(PaymentType paymentType, BigDecimal amount, Seller seller) {
        this.amount = amount;
        this.paymentType = paymentType;
        this.seller = seller;
    }
    public Transaction() {}
}