package com.pricedrop.services;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.dao.ProductRepository;
import com.pricedrop.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductCheckPrice {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApiRepository apiRepository;

    @Scheduled(fixedRate = 10000)
    public String CheckPrice() throws IOException, InterruptedException {
        List<Product> productList = productRepository.findAll();
        for(Product products : productList){
            if((products.getT_price()) >= ProductService.getCurrentPrice(UrlCoding.extractProductName(products.getP_url()))){
                System.out.println("send notification");
        }
        return null;
    }
        return null;}
}
