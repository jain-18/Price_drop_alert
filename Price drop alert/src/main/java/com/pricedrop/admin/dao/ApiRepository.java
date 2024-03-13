package com.pricedrop.admin.dao;

import com.pricedrop.admin.entities.Productapi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ApiRepository extends JpaRepository<Productapi,Integer> {
    @Query("SELECT p FROM Productapi p WHERE p.product_name = :productName")
    Optional<Productapi> findByProductName(String productName);
    @Query("SELECT p FROM Productapi p WHERE p.product_url = :productUrl")
    Productapi findByProductUrl(String productUrl);

}
