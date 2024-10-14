package com.gautama.crmshift;

import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class MainController {

    @Autowired
    private SellerService sellerService;

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        Seller createdSeller = sellerService.createSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeller);
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.findAllSellers();
    }
}
