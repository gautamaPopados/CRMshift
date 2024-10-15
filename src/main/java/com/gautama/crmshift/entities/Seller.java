package com.gautama.crmshift.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactInfo;
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Seller(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public Seller() {

    }
}