package com.pricedrop.Controller;

import com.pricedrop.dao.ProductRepository;
import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
            user.getProduct().add(product);
            product.setUser(user);
            this.userRepository.save(user);
            session.setAttribute("message", new Message("Your product has been Added!!", " alert-success "));
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

        }catch (Exception e){
            e.printStackTrace();
        }
        return "product_list";
    }

    @RequestMapping("/deleteproduct/{productID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String DeleteProduct(@PathVariable("productID")String productID){
        int pid = Integer.parseInt(productID);
        productRepository.deleteById(pid);
        return "redirect:/user/product-list";
    }

    @RequestMapping("/editProduct/{productID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String editForm(@PathVariable("productID") String productID,Model model){
        int pid = Integer.parseInt(productID);
        Product product = this.productRepository.getReferenceById(pid);
        model.addAttribute("product",product);
        return "edit";
    }


    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String Update(@ModelAttribute Product product, @RequestParam("p_id") Integer pid,Principal principal){
        product.setP_id(pid);
        User user = this.userRepository.getUserByUserName(principal.getName());
        product.setUser(user);
        productRepository.save(product);
        return "redirect:/user/product-list";
    }

}
