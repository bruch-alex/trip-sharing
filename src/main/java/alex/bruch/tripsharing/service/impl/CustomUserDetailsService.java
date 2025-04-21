package alex.bruch.tripsharing.service.impl;

import alex.bruch.tripsharing.model.Role;
import alex.bruch.tripsharing.model.UserLogin;
import alex.bruch.tripsharing.repo.RoleRepository;
import alex.bruch.tripsharing.repo.UserLoginRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(UserLoginRepository userLoginRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userLoginRepository = userLoginRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin login = userLoginRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        System.out.println("Logged in");

        List<SimpleGrantedAuthority> authorities = login.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new User(
                login.getEmail(),
                login.getPassword(),
                login.getEnabled(),
                true, true, true,
                authorities
        );
    }

    public void createUser(String email, String password) {
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserLogin userLogin = new UserLogin();

        userLogin.setEmail(email);
        userLogin.setPassword(passwordEncoder.encode(password));
        userLogin.setEnabled(true);
        userLogin.setRoles(List.of(role));

        userLoginRepository.save(userLogin);
    }
}
