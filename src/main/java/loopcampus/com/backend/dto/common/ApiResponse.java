package loopcampus.com.backend.dto.common;


public record ApiResponse<T>(
        T data,
        ApiError error,
        ApiMeta meta
) {
    public static <T> ApiResponse<T> success(T data, ApiMeta meta) {
        return new ApiResponse<>(data, null, meta);
    }

    public static <T> ApiResponse<T> fail(ApiError error, ApiMeta meta) {
        return new ApiResponse<>(null, error, meta);
    }
}