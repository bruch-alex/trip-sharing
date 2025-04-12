package alex.bruch.tripsharing.controller.web;

import alex.bruch.tripsharing.service.impl.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register-page";
    }

    @PostMapping("/register")
    public String postRegister(Model model){
        model.addAttribute("user", user);
        return "register-success";
    }
}
