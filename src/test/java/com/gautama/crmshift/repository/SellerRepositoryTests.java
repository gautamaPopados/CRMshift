package com.gautama.crmshift.repository;

import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.entities.Transaction;
import com.gautama.crmshift.enums.PaymentType;
import com.gautama.crmshift.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SellerRepositoryTests {

    @Autowired
    private SellerRepository sellerRepository;

    @BeforeEach
    public void setUp() {
        sellerRepository.deleteAll();

        Seller seller1 = new Seller(1L, "username1", "example@example.com", LocalDateTime.now());
        Seller seller2 = new Seller(2L, "username2", "example@example.com", LocalDateTime.now());

        Transaction transaction1 = new Transaction(1L, BigDecimal.valueOf(1000), PaymentType.CARD, LocalDateTime.now(), seller1);
        Transaction transaction2 = new Transaction(2L, BigDecimal.valueOf(1500), PaymentType.CASH, LocalDateTime.now(), seller1);
        Transaction transaction3 = new Transaction(3L, BigDecimal.valueOf(3000), PaymentType.TRANSFER, LocalDateTime.now(), seller2);

        seller1.setTransactions(Arrays.asList(transaction1, transaction2));
        seller2.setTransactions(Arrays.asList(transaction3));

        List<Seller> sellers = Arrays.asList(seller1, seller2);

        sellerRepository.saveAll(sellers);
    }

    @Test
    public void SellerRepository_findMostProductiveSeller() {
        Long id = 2L;
        String name = "username2";
        String email = "example@example.com";

        Seller mostProductiveSeller = sellerRepository.findMostProductiveSeller(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        assertEquals(id, mostProductiveSeller.getId());
        assertEquals(name, mostProductiveSeller.getName());
        assertEquals(email, mostProductiveSeller.getContactInfo());
    }


    @Test
    public void SellerRepository_findSellersBelowThreshold() {
        Integer expectedSellersCount = 1;

        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        BigDecimal threshold = BigDecimal.valueOf(2600);

        List<Seller> result = sellerRepository.findSellersBelowThreshold(startDate, endDate, threshold);

        assertEquals(expectedSellersCount, result.size());

    }
}
