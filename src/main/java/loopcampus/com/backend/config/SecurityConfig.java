package loopcampus.com.backend.config;

import lombok.RequiredArgsConstructor;
import loopcampus.com.backend.security.JwtAuthFilter;
import loopcampus.com.backend.security.RestAuthHandlers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@EnableConfigurationProperties(CorsProps.class)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsProps corsProps;
    private final JwtAuthFilter jwtAuthFilter;
    private final RestAuthHandlers.EntryPoint entryPoint;
    private final RestAuthHandlers.DeniedHandler deniedHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          RestAuthHandlers.EntryPoint entryPoint,
                          RestAuthHandlers.DeniedHandler deniedHandler,
                          CorsProps corsProps) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.entryPoint = entryPoint;
        this.deniedHandler = deniedHandler;
        this.corsProps = corsProps;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(corsProps.allowedOrigins());
        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
