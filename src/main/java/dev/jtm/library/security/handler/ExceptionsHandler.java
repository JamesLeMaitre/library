package dev.jtm.library.security.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import dev.jtm.library.security.response.HttpErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ExceptionsHandler implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ACCOUNT_LOCKED = "Votre compte a été bloqué. Veuillez contacter l'administration";
    private static final String METHOD_IS_NOT_ALLOWED = "Cette méthode de demande n'est pas autorisée sur ce point de terminaison. Veuillez envoyer une demande %s";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Une erreur s'est produite lors du traitement de la demande";
    private static final String INCORRECT_CREDENTIALS = "Nom d'utilisateur/mot de passe incorrect. Veuillez réessayer";
    private static final String ACCOUNT_DISABLED = "Votre compte a été désactivé. S'il s'agit d'une erreur, veuillez contacter l'administration";
    private static final String ERROR_PROCESSING_FILE = "Une erreur s'est produite lors du traitement du fichier";
    private static final String NOT_ENOUGH_PERMISSION = "Vous n'avez pas assez d'autorisation";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpErrorResponse> accountDisabledException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpErrorResponse> badCredentialsException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpErrorResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpErrorResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpErrorResponse> tokenExpiredException(TokenExpiredException exception) {
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpErrorResponse> noHandlerFoundException(NoHandlerFoundException exception) {
        return createHttpResponse(BAD_REQUEST, "This page was not found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        System.out.println(exception.getAllErrors());
        return createValidationHttpResponse(BAD_REQUEST, "Validation errors", exception.getFieldErrors());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpErrorResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod httpMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, httpMethod));
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpErrorResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpErrorResponse> ioException(IOException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    private ResponseEntity<HttpErrorResponse> createHttpResponse(HttpStatus status, String message) {
        HttpErrorResponse httpResponse = new HttpErrorResponse();
        httpResponse.setStatus(status);
        httpResponse.setStatusCode(status.value());
        httpResponse.setMessage(message);
        httpResponse.setReason(status.getReasonPhrase());
        return new  ResponseEntity<>(httpResponse, status);
    }

    private ResponseEntity<HttpErrorResponse> createValidationHttpResponse(HttpStatus status, String message, List<FieldError> errors) {
        HttpErrorResponse httpResponse = new HttpErrorResponse();
        httpResponse.setStatus(status);
        httpResponse.setStatusCode(status.value());
        httpResponse.setMessage(message);
        httpResponse.setReason(status.getReasonPhrase());
        httpResponse.setValidations(errors);
        return new  ResponseEntity<>(httpResponse, status);
    }
}
