package loopcampus.com.backend.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


/*
* put requestId for every request
* */
@Component
public class RequestIdFilter implements Filter {
//    request Id
    public static final String ATTR = "requestId";
//    name of Request Id in httpRequest from client
    public static final String HEADER = "X-Request-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

//        change to HttpServletRequest from ServletRequest, response too.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String rid = req.getHeader(HEADER);
//        if httpHeader does not have HEADER named X-Request-Id, then it makes new one
        if (rid == null || rid.isBlank()) rid = UUID.randomUUID().toString();

        req.setAttribute(ATTR, rid);
        res.setHeader(HEADER, rid);

        chain.doFilter(request, response);
    }
}


/*
* text expectation from client
*   GET /api/users HTTP/1.1
    Host: example.com
    User-Agent: Chrome/...
    Authorization: Bearer abc...
    X-Request-Id: 123e4567-e89b-12d3-a456-426614174000
* */