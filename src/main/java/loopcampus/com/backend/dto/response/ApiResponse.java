package loopcampus.com.backend.dto.response;

import java.time.Instant;

public record ApiResponse<T>(
        T data,
        ApiError error,
        ApiMeta meta
) {
    public static <T> ApiResponse<T> success(T data, ApiMeta meta) {
        return new ApiResponse<>(data, null, meta);
    }

    public static <T> ApiResponse<T> error(ApiError error, ApiMeta meta) {
        return new ApiResponse<>(null, error, meta);
    }

    public static ApiMeta meta(String requestId, String path) {
        return new ApiMeta(requestId, Instant.now(), path);
    }
}
