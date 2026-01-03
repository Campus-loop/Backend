package loopcampus.com.backend.controller.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import loopcampus.com.backend.dto.auth.request.LoginRequest;
import loopcampus.com.backend.dto.auth.request.RegisterRequest;
import loopcampus.com.backend.dto.auth.response.LoginResponse;
import loopcampus.com.backend.dto.auth.response.RefreshResponse;
import loopcampus.com.backend.dto.auth.response.RegisterResponse;
import loopcampus.com.backend.dto.common.ApiResponse;
import loopcampus.com.backend.dto.common.MessageResponse;
import loopcampus.com.backend.error.ApiException;
import loopcampus.com.backend.enumTypes.ErrorCode;
import loopcampus.com.backend.service.auth.AuthService;
import loopcampus.com.backend.util.CookieUtil;
import loopcampus.com.backend.util.MetaFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    public AuthController(AuthService authService, CookieUtil cookieUtil) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest req,
            HttpServletRequest servletReq
    ) {
        System.out.println(req.toString());
        var data = authService.register(req);
        var meta = MetaFactory.from(servletReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data, meta));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest req,
            HttpServletRequest servletReq
    ) {
        var result = authService.login(req);

        // refresh 쿠키 설정
        // maxAge는 refreshExpSeconds와 동일한 값으로 맞추는게 보통 좋음(여기선 14일 고정)
        ResponseCookie cookie = cookieUtil.refreshCookie(result.refreshRaw(), 60L * 60 * 24 * 14);

        var meta = MetaFactory.from(servletReq);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(result.body(), meta));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshResponse>> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletRequest servletReq
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new ApiException(ErrorCode.AUTH_REFRESH_INVALID);
        }
        var data = authService.refresh(refreshToken);
        var meta = MetaFactory.from(servletReq);
        return ResponseEntity.ok(ApiResponse.success(data, meta));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<MessageResponse>> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletRequest servletReq
    ) {
        if (refreshToken != null && !refreshToken.isBlank()) {
            authService.logout(refreshToken);
        }
        ResponseCookie clear = cookieUtil.clearRefreshCookie();
        var meta = MetaFactory.from(servletReq);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clear.toString())
                .body(ApiResponse.success(new MessageResponse("Logged out."), meta));
    }
}
