package alex.bruch.tripsharing.service;

import alex.bruch.tripsharing.model.UserLogin;
import alex.bruch.tripsharing.repo.UserLoginRepository;
import alex.bruch.tripsharing.service.impl.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomUserServiceTest {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserLoginRepository loginRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testCreateUser_success() {
        String email = "testcase@example.com";
        String password = "secure123";

        userService.createUser(email, password);

        // Check login
        Optional<UserLogin> savedLoginOpt = loginRepo.findByEmail(email);
        assertTrue(savedLoginOpt.isPresent());

        UserLogin savedLogin = savedLoginOpt.get();
        assertTrue(passwordEncoder.matches(password, savedLogin.getPassword()));
    }
}

