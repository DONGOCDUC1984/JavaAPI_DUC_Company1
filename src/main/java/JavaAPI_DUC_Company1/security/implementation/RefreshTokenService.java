package JavaAPI_DUC_Company1.security.implementation;

import JavaAPI_DUC_Company1.model.auth.RefreshToken;
import JavaAPI_DUC_Company1.model.auth.User;
import JavaAPI_DUC_Company1.repository.auth.RefreshTokenRepository;
import JavaAPI_DUC_Company1.security.interfaces.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService
        implements IRefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private final RefreshTokenRepository repository;

    public RefreshTokenService(
            RefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {

        RefreshToken token =
                repository.findByUser(user)
                        .orElse(new RefreshToken());

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(
                Instant.now().plusMillis(refreshExpiration));

        return repository.save(token);
    }

    @Override
    public RefreshToken verifyExpiration(
            RefreshToken token) {
        if (token.getExpiryDate()
                .isBefore(Instant.now())) {
            repository.delete(token);
            throw new RuntimeException(
                    "Refresh token expired");
        }
        return token;
    }

    @Override
    public RefreshToken findByToken(String token) {
        return repository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Refresh token not found"));

    }

    @Override
    public void delete(RefreshToken token) {
        repository.delete(token);
    }
}
