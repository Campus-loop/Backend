package loopcampus.com.backend.dto.request.auth;

public record RegisterResponse(
        String accessToken,
        UserSummary user
) {
}
