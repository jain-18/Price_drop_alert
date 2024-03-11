package com.pricedrop.services;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiProduct {
    public class Root{
        private int product_id;
        private String product_url;
        private String product_name;
        private String product_image;
        private String product_price;
    }
}
