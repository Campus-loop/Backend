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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults()) // ✅ CorsConfigurationSource Bean 자동 적용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight 허용
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(deniedHandler)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(" http://localhost:3000")); // 정확히 지정
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type","Authorization","X-CSRF"));
        config.setAllowCredentials(true); // ✅ 쿠키 사용
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
