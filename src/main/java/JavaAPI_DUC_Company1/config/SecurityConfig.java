package JavaAPI_DUC_Company1.config;

import JavaAPI_DUC_Company1.security.CustomUserDetailsService;
import JavaAPI_DUC_Company1.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Without @EnableMethodSecurity, the @PreAuthorize annotations will be ignored.
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {

        http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {

                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        // Authentication
                        .requestMatchers("/api/auth/**").permitAll()
                        // Public GET APIs
                        .requestMatchers(HttpMethod.GET, "/api/productCategory/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/provinceCity/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/district/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/bookCategory/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/book/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/potteryCategory/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/pottery/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/furnitureCategory/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/furniture/**").permitAll()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService service,
            PasswordEncoder encoder) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:3000"));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }
}