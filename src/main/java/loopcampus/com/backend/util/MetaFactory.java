package loopcampus.com.backend.util;

import jakarta.servlet.http.HttpServletRequest;
import loopcampus.com.backend.dto.common.ApiMeta;
import loopcampus.com.backend.filter.RequestIdFilter;

import java.time.Instant;

public class MetaFactory {
    private MetaFactory() {}

    public static ApiMeta from(HttpServletRequest req) {
        Object rid = req.getAttribute(RequestIdFilter.ATTR);
        String requestId = rid != null ? rid.toString() : "unknown";
        return new ApiMeta(requestId, Instant.now(), req.getRequestURI());
    }
}
