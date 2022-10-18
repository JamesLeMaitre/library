package dev.jtm.library.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jtm.library.security.filters.JwtAuthenticationEntryPoint;
import dev.jtm.library.security.response.HttpErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static dev.jtm.library.security.utils.constants.JavaConstant.ACCESS_DENIED_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        HttpErrorResponse errorResponse = new HttpErrorResponse();
        errorResponse.setStatus(UNAUTHORIZED);
        errorResponse.setStatusCode(UNAUTHORIZED.value());
        errorResponse.setReason(UNAUTHORIZED.getReasonPhrase());
        JwtAuthenticationEntryPoint.setMessage(httpServletResponse, errorResponse, ACCESS_DENIED_MESSAGE);
    }
}
