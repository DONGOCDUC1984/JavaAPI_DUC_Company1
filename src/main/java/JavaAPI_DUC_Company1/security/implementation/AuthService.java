package JavaAPI_DUC_Company1.security.implementation;

import JavaAPI_DUC_Company1.exception.UsernameAlreadyExistsException;
import JavaAPI_DUC_Company1.model.auth.*;
import JavaAPI_DUC_Company1.repository.auth.RoleRepository;
import JavaAPI_DUC_Company1.repository.auth.UserRepository;
import JavaAPI_DUC_Company1.security.GoogleTokenVerifier;
import JavaAPI_DUC_Company1.security.JwtService;
import JavaAPI_DUC_Company1.security.interfaces.IAuthService;
import JavaAPI_DUC_Company1.security.interfaces.IRefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IRefreshTokenService refreshTokenService;
    private final GoogleTokenVerifier googleTokenVerifier;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       IRefreshTokenService refreshTokenService, GoogleTokenVerifier googleTokenVerifier) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.googleTokenVerifier = googleTokenVerifier;
    }

    @Override
    public void register(RegisterRequest request) {
        createUser(request,"ROLE_USER");
    }

    @Override
    public void registerAdmin(RegisterRequest request) {
        createUser(request,"ROLE_ADMIN");
    }
    private void createUser (RegisterRequest request, String roleName) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        Role role = roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(role);
        userRepository.save(user);
    }
    @Override
    public AuthResult login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        String accessToken  = jwtService.generateToken(user.getUsername(),
                user.getRole().getName());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResult(accessToken,refreshToken.getToken());
    }

    @Override
    public AuthResult googleLogin(GoogleLoginRequest request) {

        // 1. Verify Google's ID token
        var payload = googleTokenVerifier.verify(request.getToken());

        // 2. Get information from verified Google token
        String googleId = payload.getSubject();
        String email = payload.getEmail();

        // 3. Make sure email is verified
        if (!Boolean.TRUE.equals(payload.getEmailVerified())) {
            throw new RuntimeException("Google email is not verified");
        }

        // 4. Find existing user
        User user = userRepository
                .findByGoogleId(googleId)
                .orElse(null);

        // 5. If user doesn't exist, create one
        if (user == null) {
            user = userRepository
                    .findByEmail(email)
                    .orElse(null);
        }

        if (user == null) {

            Role role = roleRepository
                    .findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

            user = new User();

            user.setUsername(email);
            user.setEmail(email);
            user.setGoogleId(googleId);

            /*
             * Google users don't login using our password.
             * Therefore we don't need a real password here.
             */
            user.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));
            user.setRole(role);
            user = userRepository.save(user);

        } else {
            /*
             * Existing account.
             * Connect it with Google if necessary.
             */
            if (user.getGoogleId() == null) {
                user.setGoogleId(googleId);
                userRepository.save(user);
            }
        }

        // 6. Generate YOUR normal JWT
        String accessToken = jwtService.generateToken(user.getUsername(),
                        user.getRole().getName());

        // 7. Create YOUR normal refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // 8. Return the same structure as normal login
        return new AuthResult(accessToken, refreshToken.getToken());
    }

    @Override
    public LoginResponse refreshToken(String refreshTokenValue, HttpServletResponse response)
    {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue);
        refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        refreshTokenService.delete(refreshToken);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        ResponseCookie cookie =
                ResponseCookie.from("refreshToken", newRefreshToken.getToken())
                        .httpOnly(true)
                        .secure(false)
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(Duration.ofDays(7))
                        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String accessToken = jwtService.generateToken(user.getUsername(),
                user.getRole().getName());

        return new LoginResponse(accessToken);
    }

    @Override
    public void logout(String refreshToken) {

        RefreshToken token =
                refreshTokenService.findByToken(refreshToken);

        refreshTokenService.delete(token);
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String username = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
