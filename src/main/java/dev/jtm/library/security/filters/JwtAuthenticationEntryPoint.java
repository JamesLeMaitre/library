package dev.jtm.library.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jtm.library.security.response.HttpErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static dev.jtm.library.security.utils.constants.JavaConstant.FORBIDDEN_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        HttpErrorResponse errorResponse = new HttpErrorResponse();
        errorResponse.setStatus(FORBIDDEN);
        errorResponse.setStatusCode(FORBIDDEN.value());
        errorResponse.setReason(FORBIDDEN.getReasonPhrase());
        setMessage(response, errorResponse, FORBIDDEN_MESSAGE);
        return;
    }

    public static void setMessage(HttpServletResponse response, HttpErrorResponse errorResponse, String forbiddenMessage) throws IOException {
        errorResponse.setMessage(forbiddenMessage);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }
}