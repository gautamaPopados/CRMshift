package com.gautama.crmshift.services;

import com.gautama.crmshift.dto.SellerDTO;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.enums.PeriodType;
import com.gautama.crmshift.exceptions.ResourceNotFoundException;
import com.gautama.crmshift.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SellerServiceTest {
    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerService sellerService;

    private Seller seller;
    private SellerDTO sellerDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller("John Doe", "john@example.com");
        seller.setId(1L);
        sellerDTO = new SellerDTO("John Doe", "john@example.com");
    }

    @Test
    public void SellerService_findAllSellers_ShouldReturnSellers() {
        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        when(sellerRepository.findAll()).thenReturn(sellers);

        List<Seller> result = sellerService.findAllSellers();

        assertEquals(1, result.size());
        assertEquals(seller.getId(), result.get(0).getId());
    }

    @Test
    public void SellerService_findSellerById_ShouldReturnSeller() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        Seller result = sellerService.findSellerById(1L);

        assertEquals(seller.getId(), result.getId());
    }

    @Test
    public void SellerService_findSellerById_ShouldThrowException_WhenNotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> sellerService.findSellerById(1L));

        assertEquals("Seller with id 1 not found", exception.getMessage());
    }

    @Test
    public void SellerService_createSeller_ShouldSaveSeller() {
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        Seller result = sellerService.createSeller(sellerDTO);

        assertEquals(seller.getName(), result.getName());
        verify(sellerRepository, times(1)).save(any(Seller.class));
    }


    @Test
    public void SellerService_deleteSeller_ShouldDeleteSeller() {
        when(sellerRepository.existsById(1L)).thenReturn(true);

        sellerService.deleteSeller(1L);

        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void SellerService_deleteSeller_ShouldThrowException_WhenNotFound() {
        when(sellerRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> sellerService.deleteSeller(1L));

        assertEquals("Seller with id 1 not found", exception.getMessage());
    }

    @Test
    public void SellerService_getMostProductiveSeller_ShouldReturnSeller() {
        LocalDate date = LocalDate.now();
        Seller mostProductiveSeller = new Seller("Most Productive", "most@example.com");
        mostProductiveSeller.setId(2L);

        when(sellerRepository.findMostProductiveSeller(any(), any())).thenReturn(mostProductiveSeller);

        Seller result = sellerService.getMostProductiveSeller(PeriodType.DAY, date);

        assertEquals(mostProductiveSeller.getId(), result.getId());
    }

    @Test
    public void SellerService_getSellersWithTotalLessThan_ShouldReturnSellers() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        BigDecimal threshold = BigDecimal.valueOf(2000);

        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        when(sellerRepository.findSellersBelowThreshold(startDate, endDate, threshold)).thenReturn(sellers);

        List<Seller> result = sellerService.getSellersWithTotalLessThan(startDate, endDate, threshold);

        assertEquals(1, result.size());
        assertEquals(seller.getId(), result.get(0).getId());
    }

    @Test
    public void SellerService_getSellersWithTotalLessThan_ShouldThrowException_WhenNotFound() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        BigDecimal threshold = BigDecimal.valueOf(2000);

        when(sellerRepository.findSellersBelowThreshold(startDate, endDate, threshold)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> sellerService.getSellersWithTotalLessThan(startDate, endDate, threshold));

        assertEquals("Seller not found", exception.getMessage());
    }
}
