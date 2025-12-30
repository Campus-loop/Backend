package loopcampus.com.backend.dto.response;

import java.time.Instant;

public record ApiMeta (
        String requestId,
        Instant timeStamp,
        String path
) {}
