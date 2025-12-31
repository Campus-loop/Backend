package loopcampus.com.backend.dto.request.auth;

public record UserSummary (
        Long id,
        String email,
        boolean emailVerified
){
}
