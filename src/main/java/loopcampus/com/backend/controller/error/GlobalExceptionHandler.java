package loopcampus.com.backend.controller.error;


import jakarta.servlet.http.HttpServletRequest;
import loopcampus.com.backend.dto.common.ApiError;
import loopcampus.com.backend.dto.common.ApiFieldError;
import loopcampus.com.backend.dto.common.ApiResponse;
import loopcampus.com.backend.enumTypes.ErrorCode;
import loopcampus.com.backend.error.ApiException;
import loopcampus.com.backend.util.MetaFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/*
*
*
* */


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApi(ApiException ex, HttpServletRequest req) {
        var ec = ex.errorCode();
        var error = ApiError.of(ec.code(), ex.getMessage(), ex.details());
        var meta = MetaFactory.from(req);
        return ResponseEntity.status(ec.status()).body(ApiResponse.fail(error, meta));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiFieldError> details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .toList();

        var error = ApiError.of(ErrorCode.VALIDATION_FAILED.code(), ErrorCode.VALIDATION_FAILED.defaultMessage(), details);
        var meta = MetaFactory.from(req);
        return ResponseEntity.badRequest().body(ApiResponse.fail(error, meta));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAny(Exception ex, HttpServletRequest req) {
        var error = ApiError.of(ErrorCode.INTERNAL_ERROR.code(), ErrorCode.INTERNAL_ERROR.defaultMessage());
        var meta = MetaFactory.from(req);
        return ResponseEntity.status(500).body(ApiResponse.fail(error, meta));
    }

    private ApiFieldError mapFieldError(FieldError fe) {
        return new ApiFieldError(fe.getField(), fe.getDefaultMessage());
    }
}
