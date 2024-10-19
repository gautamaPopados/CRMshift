package com.gautama.crmshift.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gautama.crmshift.dto.SellerDTO;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.services.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = SellerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SellerService sellerService;

    private SellerDTO sellerDTO;
    private Seller seller;

    @BeforeEach
    public void setUp() {
        seller = new Seller("John Doe", "john@example.com");
        seller.setId(1L);

        sellerDTO = new SellerDTO("John Doe", "john@example.com");
    }

    @Test
    public void SellerController_getAllSellers_ShouldReturnListOfSellers() throws Exception {
        List<Seller> sellers = Collections.singletonList(seller);
        when(sellerService.findAllSellers()).thenReturn(sellers);

        mockMvc.perform(get("/api/sellers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(seller.getName()));
    }

    @Test
    public void SellerController_getSellerById_ShouldReturnSeller() throws Exception {
        when(sellerService.findSellerById(anyLong())).thenReturn(seller);

        mockMvc.perform(get("/api/sellers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(seller.getName()));
    }

    @Test
    public void createSeller_ShouldCreateSeller() throws Exception {
        sellerService.createSeller(any(SellerDTO.class));

        mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).createSeller(any(SellerDTO.class));
    }

    @Test
    public void updateSeller_ShouldUpdateSeller() throws Exception {
        doNothing().when(sellerService).updateSeller(anyLong(), any(SellerDTO.class));

        mockMvc.perform(put("/api/sellers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).updateSeller(eq(1L), any(SellerDTO.class));
    }

    @Test
    public void deleteSeller_ShouldDeleteSeller() throws Exception {
        doNothing().when(sellerService).deleteSeller(anyLong());

        mockMvc.perform(delete("/api/sellers/{id}", 1L))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).deleteSeller(eq(1L));
    }

    @Test
    public void getMostProductiveSeller_ShouldReturnSeller() throws Exception {
        LocalDate date = LocalDate.now();
        when(sellerService.getMostProductiveSeller(any(), any())).thenReturn(seller);

        mockMvc.perform(get("/api/sellers/most-productive-in/{period}", "DAY")
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(seller.getName()));
    }

    @Test
    public void getSellersWithTransactionSumLessThan_ShouldReturnSellers() throws Exception {
        List<Seller> sellers = Collections.singletonList(seller);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        BigDecimal amount = BigDecimal.valueOf(1000);

        when(sellerService.getSellersWithTotalLessThan(any(), any(), any())).thenReturn(sellers);

        mockMvc.perform(get("/api/sellers/transactions/sum-less-than")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("amount", amount.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(seller.getName()));
    }
}
