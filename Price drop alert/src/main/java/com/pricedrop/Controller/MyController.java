package com.pricedrop.Controller;

import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ComponentScan("com.pricedrop.services.SessionHelper")
public class MyController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String Home(){
        return "home";
    }

    @RequestMapping("/signup")
    public String SignUp(){
        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute User user, HttpSession session){
        user.setRole("ROLE_USER");
        this.userRepository.save(user);
        session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
        return "redirect:/signup";
    }



}
