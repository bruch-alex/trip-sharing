package alex.bruch.tripsharing.repo;

import alex.bruch.tripsharing.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByEmail(String email);
}
