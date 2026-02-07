package br.com.atos.app.exception;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExcepetionHandler {
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request){
        
        ApiError error = new ApiError(
            ex.getStatusCode().value(),
            ex.getStatusCode().toString(),
            ex.getReason(),
            UUID.randomUUID().toString(),
            request.getRequestURI(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(ex.getStatusCode())
            .body(error);
    }
}
