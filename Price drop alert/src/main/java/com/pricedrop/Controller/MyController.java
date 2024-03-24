package com.pricedrop.Controller;

import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import com.pricedrop.services.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@ComponentScan("com.pricedrop.services.SessionHelper")
public class MyController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @RequestMapping("/")
    public String Home(){
        return "home1";
    }

    @RequestMapping("/about")
    public String About(){
        return "about1";
    }

    @RequestMapping("/signup")
    public String SignUp(){
        return "signup1";
    }

    @RequestMapping("/signin")
    public String SignIn(){
        return "signin";
    }

    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") User user, HttpSession session){
        user.setRole("ROLE_USER");
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        this.userRepository.save(user);
        session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
        return "redirect:/signup";
    }

    @PostMapping("/sendOTP")
    public String verifyUser(@ModelAttribute("user") User user, HttpSession session, Model model){
        Random random = new Random();
        model.addAttribute("title","Email");
        //generating otp of 4 digit
        int otp = random.nextInt(9999999);
        System.out.println(user.getEmail());
        System.out.println(otp);

        //write a code for send otp to email
        String subject = "OTP for verification of email from price Drop Alert";
        String message = "" +
                "<div style='border:1px solid #e2e2e2: padding:20px'>" +
                "<h1>" +
                "OTP is " +
                "<b>" + otp +
                "</n>" +
                "</h1>" +
                "</div>";
        String to = user.getEmail();

//        boolean flag = this.emailService.sendEmail(subject,message,to);
        boolean flag = true;
        if(flag){

            session.setAttribute("user",user);
            session.setAttribute("myotp",otp);
            session.setAttribute("email",user.getEmail());
            session.setAttribute("message",new Message("We have sent OTP to your email.."," alert-success "));

            return "verify_otp";
        }
        else{
            session.setAttribute("message",new Message("Enter your correct email-Id!!"," alert-danger "));
            return "signup";
        }

    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String uotp,HttpSession session){
        try {
            Integer ogOTP = (Integer) session.getAttribute("myotp");
            String numericRegex = "\\d+";
            if (uotp.matches(numericRegex)) {
                int uOTP = Integer.parseInt(uotp);
                if (uOTP == ogOTP) {
                    User user1 = (User) session.getAttribute("user");
                    System.out.println(user1.getEmail());
                    user1.setRole("ROLE_USER");
                    user1.setPassword(passwordEncoder.encode(user1.getPassword()));
                    this.userRepository.save(user1);
                    session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
                    return "redirect:/signup";
                } else {
                    session.setAttribute("message", new Message("Wrong otp", "alert-success"));
                    return "verify_otp";
                }
            } else {
                session.setAttribute("message", new Message("Enter number only", "alert-success"));
                return "verify_otp";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "signup";
        }
    }




}
