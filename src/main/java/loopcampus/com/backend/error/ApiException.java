package loopcampus.com.backend.error;


import loopcampus.com.backend.dto.common.ApiFieldError;

import java.util.List;
import java.util.Objects;

public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final List<ApiFieldError> details;

    public ApiException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public ApiException(ErrorCode errorCode, String overrideMessage) {
        this(errorCode, overrideMessage, null);
    }

    public ApiException(ErrorCode errorCode, List<ApiFieldError> details) {
        this(errorCode, null, details);
    }

    public ApiException(ErrorCode errorCode, String overrideMessage, List<ApiFieldError> details) {
        super(resolveMessage(errorCode, overrideMessage));
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.details = (details == null) ? List.of() : List.copyOf(details);
    }

    private static String resolveMessage(ErrorCode errorCode, String overrideMessage) {
        if (overrideMessage == null || overrideMessage.isBlank()) {
            return errorCode.defaultMessage();
        }
        return overrideMessage;
    }

    public ErrorCode errorCode() { return errorCode; }
    public List<ApiFieldError> details() { return details; }
}
