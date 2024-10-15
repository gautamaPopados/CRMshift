package com.gautama.crmshift.repositories;

import com.gautama.crmshift.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query("SELECT s FROM Seller s WHERE (SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.seller = s) < :amount")
    List<Seller> findSellersWithTransactionSumLessThan(@Param("amount") BigDecimal amount);

    @Query("SELECT s FROM Seller s JOIN Transaction t ON s.id = t.seller.id " +
            "GROUP BY s.id ORDER BY SUM(t.amount) DESC LIMIT 1")
    Seller findSellerWithMostTransactionAmount();
}
