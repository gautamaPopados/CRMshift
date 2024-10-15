package com.gautama.crmshift.services;
import java.math.BigDecimal;
import java.util.List;

import com.gautama.crmshift.dto.SellerDTO;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.exceptions.ResourceNotFoundException;
import com.gautama.crmshift.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public List<Seller> findAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller findSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Seller with id " + id + " not found"));
    }

    public void createSeller(SellerDTO sellerDTO) {
        Seller seller = new Seller(sellerDTO.getName(), sellerDTO.getContactInfo());
        seller.setRegistrationDate(LocalDateTime.now());
        sellerRepository.save(seller);
    }

    public void updateSeller(Long id, Seller sellerDetails) {
        sellerRepository.findById(id).ifPresentOrElse(seller -> {
            seller.setName(sellerDetails.getName());
            seller.setContactInfo(sellerDetails.getContactInfo());
            sellerRepository.save(seller);
        }, () -> {
            throw new ResourceNotFoundException("Seller with id " + id + " not found");
        }
        );
    }

    public void deleteSeller(Long id) {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Seller with id " + id + " not found");
        }
    }

    public Seller findMostProductiveSeller() {
        return sellerRepository.findSellerWithMostTransactionAmount();
    }

    public List<Seller> findSellersWithTransactionSumLessThan(BigDecimal amount) {
        return sellerRepository.findSellersWithTransactionSumLessThan(amount);
    }
}
