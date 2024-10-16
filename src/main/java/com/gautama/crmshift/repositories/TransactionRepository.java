package com.gautama.crmshift.repositories;

import com.gautama.crmshift.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.seller.id = :sellerId")
    List<Transaction> findTransactionsBySeller(@Param("sellerId") Long sellerId);
}
