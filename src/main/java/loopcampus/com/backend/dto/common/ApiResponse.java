package loopcampus.com.backend.dto.common;



/*
*   API Envelope for every response format
*
*   Success :
*   { data: ..., error : null, meta : ...}
*   fail :
*   { data : null, error : ..., meta : ...}
* */
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