package com.gautama.crmshift.controllers;

import com.gautama.crmshift.dto.SellerDTO;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.services.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Seller Controller")
@RestController
@RequestMapping("/api")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Operation(summary = "Получить список всех продавцов")
    @GetMapping("/sellers")
    public List<Seller> getAllSellers() {
        return sellerService.findAllSellers();
    }

    @Operation(summary = "Получить продавца по ID")
    @GetMapping("/sellers/{id}")
    public Seller getSellerById(@PathVariable Long id) {
        return sellerService.findSellerById(id);
    }

    @Operation(summary = "Добавить продавца")
    @PostMapping("/sellers")
    public void createSeller(@RequestBody SellerDTO seller) {
        sellerService.createSeller(seller);
    }

    @Operation(summary = "Обновить информацию о продавце")
    @PutMapping("/sellers/{id}")
    public void updateSeller(@PathVariable Long id, @RequestBody SellerDTO sellerDetails) {
        sellerService.updateSeller(id, sellerDetails);
    }

    @Operation(summary = "Удалить продавца")
    @DeleteMapping("/sellers/{id}")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
    }

    @Operation(summary = "Получить самого продуктивного продавца")
    @GetMapping("/sellers/most-productive")
    public Seller getMostProductiveSeller() {
        return sellerService.findMostProductiveSeller();
    }

    @Operation(summary = "Получить список продавцов с суммой транзакций меньше указанной")
    @GetMapping("/sellers/transactions/sum-less-than/{amount}")
    public List<Seller> getSellersWithTransactionSumLessThan(@PathVariable BigDecimal amount) {
        return sellerService.findSellersWithTransactionSumLessThan(amount);
    }
}
