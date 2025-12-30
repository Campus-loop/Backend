package loopcampus.com.backend.dto.response;

public record ApiFieldError(
        String field,
        String reason
) {
    public static ApiFieldError of(String field, String reason) {
        return new ApiFieldError(field, reason);
    }
}
