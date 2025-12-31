package loopcampus.com.backend.error;



import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "Invalid request."),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_EMAIL_ALREADY_EXISTS", "Email already exists."),
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_INVALID_CREDENTIALS", "Email or password is incorrect."),
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_UNAUTHORIZED", "Unauthorized."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_FORBIDDEN", "Forbidden."),
    AUTH_REFRESH_INVALID(HttpStatus.UNAUTHORIZED, "AUTH_REFRESH_INVALID", "Session expired. Please log in again."),
    AUTH_REFRESH_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_REFRESH_EXPIRED", "Session expired. Please log in again."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Unexpected error.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus status() { return status; }
    public String code() { return code; }
    public String message() { return message; }
}
