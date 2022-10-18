package dev.jtm.library.security.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
@RequiredArgsConstructor
public class HttpErrorResponse {
    private HttpStatus status;

    private int statusCode;

    private String message;

    private String reason;

    private List<FieldError> validations;
}
