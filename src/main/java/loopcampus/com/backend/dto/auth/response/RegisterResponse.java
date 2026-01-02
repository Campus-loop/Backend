package loopcampus.com.backend.dto.auth.response;

public record RegisterResponse(
        Long id,
        String email,
        boolean isEmailVerified
) {
}
