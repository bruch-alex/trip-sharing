package alex.bruch.tripsharing.config;

import alex.bruch.tripsharing.model.Role;
import alex.bruch.tripsharing.repo.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            createRoleIfNotFound("ROLE_USER", roleRepository);
            createRoleIfNotFound("ROLE_ADMIN", roleRepository);
            createRoleIfNotFound("ROLE_MODERATOR", roleRepository);
        };
    }

    private void createRoleIfNotFound(String roleName, RoleRepository roleRepository) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}