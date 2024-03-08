package com.pricedrop.admin.dao;

import com.pricedrop.admin.entities.Productapi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ApiRepository extends JpaRepository<Productapi,Integer> {
}
