package com.gautama.crmshift.repositories;

import com.gautama.crmshift.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT t.seller, SUM(t.amount) AS totalAmount FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.seller HAVING SUM(t.amount) < :threshold")
    List<Seller> findSellersBelowThreshold(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate,
                                           @Param("threshold") BigDecimal threshold);

    @Query("SELECT t.seller, SUM(t.amount) AS totalAmount FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.seller ORDER BY totalAmount DESC LIMIT 1")
    Seller findMostProductiveSeller(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
