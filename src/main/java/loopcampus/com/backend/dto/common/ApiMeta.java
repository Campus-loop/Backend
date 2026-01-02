package loopcampus.com.backend.dto.common;

import java.time.Instant;

public record ApiMeta (
        String requestId,
        Instant timeStamp,
        String path
) {}
