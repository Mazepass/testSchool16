package ru.mazepa.FirsSecurity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

@GetMapping("/profile")
    public String profile() {
        return "/profile" ;

    }


    public String editProfile() {


    return "/edit" ;
    }




}
