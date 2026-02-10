package md.utm2026.mvc.web;

import jakarta.servlet.http.HttpServletRequest;
import md.utm2026.mvc.exception.ApiError;
import md.utm2026.mvc.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> notFound(UserNotFoundException ex, HttpServletRequest req) {
        logger.warn("User not found: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        logger.warn("Validation failed: {}", ex.getMessage());
        List<ApiError.FieldViolation> v = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toViolation)
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), v);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
        logger.error("Unexpected error handling request {}", req.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI(), List.of());
    }

    private ApiError.FieldViolation toViolation(FieldError fe) {
        return new ApiError.FieldViolation(fe.getField(), fe.getDefaultMessage());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, String path, List<ApiError.FieldViolation> v) {
        ApiError body = new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, path, v);
        return ResponseEntity.status(status).body(body);
    }
}

