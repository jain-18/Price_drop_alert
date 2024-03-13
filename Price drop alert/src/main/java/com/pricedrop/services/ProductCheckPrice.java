package com.pricedrop.services;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.dao.ProductRepository;
import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCheckPrice {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;



    @Scheduled(fixedRate = 10000)
    public void CheckPrice() throws IOException, InterruptedException {
        List<Product> productList = productRepository.findAll();
        for(Product products : productList){
            if((products.getT_price()) >= ProductService.getCurrentPrice(UrlCoding.extractProductName(products.getP_url()))){
                System.out.println("send notification");
                sendNotification(products,ProductService.getCurrentPrice(UrlCoding.extractProductName(products.getP_url())));
                this.productRepository.deleteById(products.getP_id());
        }
    }
    }

    public void sendNotification(Product product,Double price){
        int u_id = product.getUser().getU_id();
        Optional<User> user = this.userRepository.findById(u_id);
        boolean b = this.emailService.sendEmail("Price has been dropped !!","The price of the product "+UrlCoding.extractProductName(product.getP_url())+"has dropped to "+price,user.get().getEmail());



    }
}
