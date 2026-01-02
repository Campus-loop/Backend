package loopcampus.com.backend.dto.auth.response;

public record LoginResponse(
        String accessToken,
        MemberSummary user
) {
}
