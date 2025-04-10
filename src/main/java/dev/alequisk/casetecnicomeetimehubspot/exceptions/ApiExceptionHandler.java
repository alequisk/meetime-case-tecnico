package dev.alequisk.casetecnicomeetimehubspot.exceptions;

import dev.alequisk.casetecnicomeetimehubspot.dtos.DefaultErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(InternalApiException.class)
    public ResponseEntity<DefaultErrorResponse> handleInternalApiException(InternalApiException e) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<DefaultErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RateLimitReachException.class)
    public ResponseEntity<DefaultErrorResponse> handleRateLimitReachException(RateLimitReachException e) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<DefaultErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity<DefaultErrorResponse> handleConflictDataException(ConflictDataException e) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
