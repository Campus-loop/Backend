package loopcampus.com.backend.error;



import org.springframework.http.HttpStatus;


public enum ErrorCode {

    // 400
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "Invalid request."),

    // 409
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_EMAIL_ALREADY_EXISTS", "Email already exists."),

    // 401
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_INVALID_CREDENTIALS", "Email or password is incorrect."),
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_UNAUTHORIZED", "Unauthorized."),
    AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH_TOKEN_INVALID", "Invalid token."),
    AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_TOKEN_EXPIRED", "Session expired. Please log in again."),

    // 403
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_FORBIDDEN", "Forbidden."),

    // 500
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Unexpected error.");

    private final Meta meta;

    ErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.meta = new Meta(status, code, defaultMessage);
    }

    public HttpStatus status() {
        return meta.status();
    }

    public String code() {
        return meta.code();
    }

    public String defaultMessage() {
        return meta.defaultMessage();
    }

    /**
     * if i want to use different message (ex: validation detail message).
     * only use safe message for client
     */
    public ErrorDetail detail(String messageOverride) {
        String msg = (messageOverride == null || messageOverride.isBlank())
                ? meta.defaultMessage()
                : messageOverride;
        return new ErrorDetail(meta.status(), meta.code(), msg);
    }

    public ErrorDetail detail() {
        return new ErrorDetail(meta.status(), meta.code(), meta.defaultMessage());
    }

    private record Meta(HttpStatus status, String code, String defaultMessage) {}

    public record ErrorDetail(HttpStatus status, String code, String message) {}
}
