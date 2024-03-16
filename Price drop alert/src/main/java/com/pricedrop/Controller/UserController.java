package com.pricedrop.Controller;

import com.pricedrop.dao.ProductRepository;
import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import com.pricedrop.services.ProductService;
import com.pricedrop.services.UrlCoding;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/dashboard")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String Dashboard(){
        return "dashboard";
    }
    @RequestMapping("/add-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String product(){
        return "add_product";
    }

    @RequestMapping("/added-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String AddedProduct(@ModelAttribute Product product, Principal principal, HttpSession session){
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);


            if(ProductService.getCurrentPrice(UrlCoding.extractProductName(product.getP_url())) != null && product.getP_url().contains("https://www.example.com/product/")) {
                user.getProduct().add(product);
                product.setUser(user);
                this.userRepository.save(user);
                session.setAttribute("message", new Message("Your product has been Added!!", " alert-success "));
            }else {
                session.setAttribute("message", new Message("Wrong Url!! Try again..", " alert-danger "));
            }
        }catch (Exception e){
            session.setAttribute("message",new Message("Something went wrong!! Try again.."," alert-danger "));
        }
        return "redirect:/user/add-product";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping("/product-list")
    public String List(Model model, Principal principal){
        try{
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);
            List<Product> products = user.getProduct();
            model.addAttribute("list",products);
            model.addAttribute("productService", new ProductService());
            model.addAttribute("UrlCoding",new UrlCoding());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "product_list";
    }

    @RequestMapping("/deleteproduct/{productID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String DeleteProduct(@PathVariable("productID") int productID){
        try {
            Optional<Product> product = this.productRepository.findById(productID);
            Product pro = product.get();
            User user = pro.getUser();
            user.getProduct().remove(pro);
            this.userRepository.save(user); // Update the user entity
            this.productRepository.deleteById(productID); // Delete the product

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }

        return "redirect:/user/product-list";
    }



}
