package JavaAPI_DUC_Company1.security.interfaces;

import JavaAPI_DUC_Company1.model.auth.RefreshToken;
import JavaAPI_DUC_Company1.model.auth.User;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken token);
    RefreshToken findByToken(String token);
    void delete(RefreshToken token);
}
