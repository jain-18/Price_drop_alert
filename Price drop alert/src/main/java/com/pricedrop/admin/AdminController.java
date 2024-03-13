package com.pricedrop.admin;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.services.UrlCoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private ApiRepository apiRepository;

    @RequestMapping("/getproduct")
    public List<Productapi> GetProduct(){
        return this.apiRepository.findAll();
    }

    @PostMapping("/add-product")
    public Productapi addUser(@RequestBody Productapi productapi){
        return this.apiRepository.save(productapi);
    }

    @PutMapping("/edit-product/{id}")
    public Productapi editProduct(@PathVariable int id, @RequestBody Productapi updatedProduct) {
        Optional<Productapi> optionalProduct = apiRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Productapi product = optionalProduct.get();
            product.setProduct_url(updatedProduct.getProduct_url());
            product.setProduct_name(updatedProduct.getProduct_name());
            product.setProduct_image(updatedProduct.getProduct_image());
            product.setProduct_price(updatedProduct.getProduct_price());
            return apiRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @DeleteMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable int id) {
        apiRepository.deleteById(id);
        return "Product deleted successfully";
    }

    @GetMapping("/getproductbyname/{name}")
    public Productapi getProductByName(@PathVariable String name) {
        Optional<Productapi> optionalProduct = apiRepository.findByProductName(name);
        System.out.println(name);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new RuntimeException("Product not found with name: " + name);
        }
    }
    @GetMapping("/getproductbyurl/{url}")
    public Productapi getProductByUrl(@PathVariable("url") String url) {
//        url = UrlCoding.decodeUrl(url);
        url = "https://www.example.com/product/" + url;
        Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new RuntimeException("Product not found with URL: " + url);
        }
    }

    public static String encodeUrl(String originalUrl) {
        try {
            return URLEncoder.encode(originalUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Handle encoding error
            e.printStackTrace();
            return null;
        }
    }
    public static String decodeUrl(String encodedUrl) {
        try {
            return URLDecoder.decode(encodedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Handle decoding error
            e.printStackTrace();
            return null;
        }
    }


}
