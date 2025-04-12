package alex.bruch.tripsharing.controller.web;

import alex.bruch.tripsharing.model.UserRegistrationDTO;
import alex.bruch.tripsharing.service.impl.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String getRegisterPage() {
        return "register-page";
    }

    @PostMapping("/register")
    public String postRegisterPage(@ModelAttribute UserRegistrationDTO userRegistrationDTO) {
        customUserDetailsService.createUser(userRegistrationDTO.email(), userRegistrationDTO.password());
        return "redirect:register";
    }
}
