package JavaAPI_DUC_Company1.security.interfaces;

import JavaAPI_DUC_Company1.model.auth.*;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    void register(RegisterRequest request);
    void registerAdmin(RegisterRequest request);
    AuthResult login(LoginRequest request);
    AuthResult googleLogin(GoogleLoginRequest request);
    LoginResponse refreshToken(
            String refreshToken, HttpServletResponse response);
    void logout(String refreshToken);
    User getCurrentUser();
}
