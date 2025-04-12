package alex.bruch.tripsharing.controller.api;

import alex.bruch.tripsharing.service.impl.CustomUserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public void test() {
        customUserDetailsService.createUser("test", "test");
        System.out.println(customUserDetailsService.loadUserByUsername("test"));
    }
}
