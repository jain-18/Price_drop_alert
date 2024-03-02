package com.pricedrop.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String product(){
        return "add_product";
    }
}
