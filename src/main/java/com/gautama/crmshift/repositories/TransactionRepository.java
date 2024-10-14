package com.gautama.crmshift.repositories;

import com.gautama.crmshift.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
