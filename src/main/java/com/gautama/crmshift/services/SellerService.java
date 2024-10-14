package com.gautama.crmshift.services;
import java.util.List;
import com.gautama.crmshift.entities.Seller;
import com.gautama.crmshift.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public Seller createSeller(Seller seller) {
        // Вы можете добавить здесь дополнительную логику, если это необходимо
        seller.setRegistrationDate(LocalDateTime.now());  // Устанавливаем дату регистрации
        return sellerRepository.save(seller);  // Сохраняем продавца в базу данных
    }

    // Пример получения всех продавцов
    public List<Seller> findAllSellers() {
        return sellerRepository.findAll();  // Извлекаем всех продавцов из БД
    }
}
