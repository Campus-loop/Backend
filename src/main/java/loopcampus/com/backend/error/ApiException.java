package loopcampus.com.backend.error;


import loopcampus.com.backend.dto.response.ApiFieldError;

import java.util.List;

public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final List<ApiFieldError> details;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
        this.details = null;
    }

    public ApiException(ErrorCode errorCode, String overrideMessage) {
        super(overrideMessage);
        this.errorCode = errorCode;
        this.details = null;
    }

    public ApiException(ErrorCode errorCode, String overrideMessage, List<ApiFieldError> details) {
        super(overrideMessage);
        this.errorCode = errorCode;
        this.details = details;
    }

    public ErrorCode errorCode() { return errorCode; }
    public List<ApiFieldError> details() { return details; }
}
