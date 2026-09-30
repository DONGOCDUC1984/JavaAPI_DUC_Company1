package JavaAPI_DUC_Company1.repository.auth;

import JavaAPI_DUC_Company1.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Integer> {
    // Optional : A user may not exist.
    // Therefore,instead of returning null, Spring returns: Optional<User>
    Optional<User> findByUsername(String username);
    Optional<User> findByGoogleId(String googleId);
    Optional<User> findByEmail(String email);
}
