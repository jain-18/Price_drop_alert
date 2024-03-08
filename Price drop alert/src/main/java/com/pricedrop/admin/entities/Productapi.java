package com.pricedrop.admin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Productapi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int product_id;
    private String product_url;
    private String product_name;
    private String product_image;
    private String product_price;
}
