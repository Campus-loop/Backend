package loopcampus.com.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieUtil {

    @Value("${app-cookie.secure:false}")
    private boolean secure;

    @Value("${app-cookie.same-site:Lax}")
    private String sameSite;

    @Value("${app-cookie.domain:localhost:3000}")
    private String domain; // 예: ".loopcampus.com" (서브도메인 공유 시)

    public ResponseCookie refreshCookie(String value, long maxAgeSeconds) {
        return ResponseCookie.from("refreshToken", value)
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path("/auth")
                .maxAge(maxAgeSeconds)
                .build();
    }

    public ResponseCookie clearRefreshCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path("/auth")
                .maxAge(0)
                .build();
    }

    // CSRF 토큰은 JS가 읽어야 헤더로 보낼 수 있으니 HttpOnly=false
    public ResponseCookie csrfCookie(String value, long maxAgeSeconds) {
        var b = ResponseCookie.from("csrf", value)
                .httpOnly(false)
                .secure(secure)
                .path("/")
                .sameSite(sameSite)
                .maxAge(Duration.ofSeconds(maxAgeSeconds));
        if (domain != null && !domain.isBlank()) b.domain(domain);
        return b.build();
    }


}