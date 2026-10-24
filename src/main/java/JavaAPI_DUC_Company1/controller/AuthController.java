package JavaAPI_DUC_Company1.controller;

import JavaAPI_DUC_Company1.model.auth.*;
import JavaAPI_DUC_Company1.security.interfaces.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private final IAuthService _service;

    public AuthController(IAuthService service) {
        _service = service;
    }
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        _service.register(request);
        return "Registered successfully";
    }

    @PostMapping("/registerAdmin")
    public String registerAdmin(@Valid @RequestBody RegisterRequest request) {
        _service.registerAdmin(request);
        return "Registered Admin successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResult result = _service.login(request);
        ResponseCookie cookie =
                ResponseCookie.from(
                                "refreshToken",
                                result.getRefreshToken())
                        .httpOnly(true)
                        .secure(false)      // true after HTTPS deployment
                        .path("/")
                        .maxAge(Duration.ofDays(7))
                        .sameSite("Lax")
                        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/google-login")
    public ResponseEntity<LoginResponse> googleLogin(
            @Valid @RequestBody GoogleLoginRequest request,
            HttpServletResponse response)
    {
        AuthResult result = _service.googleLogin(request);
        ResponseCookie cookie =
                ResponseCookie.from(
                                "refreshToken",
                                result.getRefreshToken())
                        .httpOnly(true)
                        .secure(false)      // true after HTTPS deployment
                        .path("/")
                        .maxAge(Duration.ofDays(7))
                        .sameSite("Lax")
                        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new LoginResponse(result.getAccessToken()));
    }

    @PostMapping("/refreshToken")
    public LoginResponse refresh(

            @CookieValue("refreshToken")
            String refreshToken,HttpServletResponse response) {

        return _service.refreshToken(refreshToken,response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response) {

        _service.logout(refreshToken);

        // Remove the cookie
        ResponseCookie cookie =
                ResponseCookie.from("refreshToken", "")
                        .httpOnly(true)
                        .secure(false)   // true in HTTPS
                        .path("/")
                        .maxAge(0)
                        .sameSite("Lax")
                        .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString());

        return ResponseEntity.ok("Logged out");
    }
}
