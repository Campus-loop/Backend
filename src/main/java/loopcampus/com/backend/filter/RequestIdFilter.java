package loopcampus.com.backend.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter implements Filter {
    public static final String ATTR = "requestId";
    public static final String HEADER = "X-Request-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String rid = req.getHeader(HEADER);
        if (rid == null || rid.isBlank()) rid = UUID.randomUUID().toString();

        req.setAttribute(ATTR, rid);
        res.setHeader(HEADER, rid);

        chain.doFilter(request, response);
    }
}
