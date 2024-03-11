package com.pricedrop.services;

import com.pricedrop.dao.ProductRepository;
import com.pricedrop.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCheckPrice {
    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedRate = 20000)
    public String CheckPrice(){
        List<Product> productList = productRepository.findAll();
        for(Product products : productList){
//            if(Double.parseDouble(String.valueOf(products.getT_price())) >= ProductService.getCurrentPrice(products.getP_url())){

//        }
        return null;
    }
        return null;}
}
