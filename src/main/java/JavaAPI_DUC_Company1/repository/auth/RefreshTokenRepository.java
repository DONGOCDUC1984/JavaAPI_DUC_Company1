package JavaAPI_DUC_Company1.repository.auth;

import JavaAPI_DUC_Company1.model.auth.RefreshToken;
import JavaAPI_DUC_Company1.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Integer> {
    //used by POST /api/auth/refresh
    Optional<RefreshToken> findByToken(String token);
    //used when the same user logs in again.
    Optional<RefreshToken> findByUser(User user);
    //used by logout
    void deleteByToken(String token);
}
