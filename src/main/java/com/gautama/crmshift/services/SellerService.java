package com.gautama.crmshift.services;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.gautama.crmshift.dto.SellerDTO;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.enums.PeriodType;
import com.gautama.crmshift.exceptions.InvalidRequestException;
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

    public Seller createSeller(SellerDTO sellerDTO) {
        if (sellerDTO.getName() == null || sellerDTO.getName().isEmpty()) {
            throw new InvalidRequestException("Seller name cannot be null or empty");
        }
        Seller seller = new Seller(sellerDTO.getName(), sellerDTO.getContactInfo());
        seller.setRegistrationDate(LocalDateTime.now());
        sellerRepository.save(seller);
        return seller;
    }

    public void updateSeller(Long id, SellerDTO sellerDetails) {
        if (sellerDetails.getName() == null || sellerDetails.getName().isEmpty()) {
            throw new InvalidRequestException("Seller name cannot be null or empty.");
        }

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

    public Seller getMostProductiveSeller(PeriodType periodType, LocalDate date) {
        LocalDate startDate;
        LocalDate endDate;

        switch (periodType) {
            case DAY:
                startDate = date;
                endDate = date;
                break;
            case MONTH:
                startDate = date.withDayOfMonth(1);
                endDate = date.withDayOfMonth(date.lengthOfMonth());
                break;
            case QUARTER:
                startDate = date.withMonth(((date.getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
                endDate = startDate.plusMonths(3).minusDays(1);
                break;
            case YEAR:
                startDate = date.withDayOfYear(1);
                endDate = date.withDayOfYear(date.lengthOfYear());
                break;
            default:
                throw new InvalidRequestException("Invalid period type");
        }
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
        System.out.println(startDateTime);
        System.out.println(endDateTime);
        Seller seller = sellerRepository.findMostProductiveSeller(startDateTime, endDateTime);

        if (seller == null) {throw new ResourceNotFoundException("Seller not found");}
        return seller;
    }

    public List<Seller> getSellersWithTotalLessThan(LocalDateTime startDate,
                                                    LocalDateTime endDate,
                                                    BigDecimal amount) {

        List<Seller> sellers = sellerRepository.findSellersBelowThreshold(startDate, endDate, amount);

        if (sellers.isEmpty()) {throw new ResourceNotFoundException("Seller not found");}
        return sellers;
    }
}
