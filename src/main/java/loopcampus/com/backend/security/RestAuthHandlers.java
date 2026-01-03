package loopcampus.com.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loopcampus.com.backend.dto.common.ApiError;
import loopcampus.com.backend.dto.common.ApiResponse;
import loopcampus.com.backend.enumTypes.ErrorCode;
import loopcampus.com.backend.util.MetaFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthHandlers {

    @Component
    public static class EntryPoint implements AuthenticationEntryPoint {
        private final ObjectMapper om;

        public EntryPoint(ObjectMapper om) { this.om = om; }

        @Override
        public void commence(HttpServletRequest req, HttpServletResponse res,
                             org.springframework.security.core.AuthenticationException authException) throws IOException {
            var error = ApiError.of(ErrorCode.AUTH_UNAUTHORIZED.code(), ErrorCode.AUTH_UNAUTHORIZED.defaultMessage());
            var meta = MetaFactory.from(req);
            var body = ApiResponse.fail(error, meta);

            res.setStatus(ErrorCode.AUTH_UNAUTHORIZED.status().value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            om.writeValue(res.getWriter(), body);
        }
    }

    @Component
    public static class DeniedHandler implements AccessDeniedHandler {
        private final ObjectMapper om;

        public DeniedHandler(ObjectMapper om) { this.om = om; }

        @Override
        public void handle(HttpServletRequest req, HttpServletResponse res,
                           AccessDeniedException accessDeniedException) throws IOException {
            var error = ApiError.of(ErrorCode.AUTH_FORBIDDEN.code(), ErrorCode.AUTH_FORBIDDEN.defaultMessage());
            var meta = MetaFactory.from(req);
            var body = ApiResponse.fail(error, meta);

            res.setStatus(ErrorCode.AUTH_FORBIDDEN.status().value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            om.writeValue(res.getWriter(), body);
        }
    }
}
