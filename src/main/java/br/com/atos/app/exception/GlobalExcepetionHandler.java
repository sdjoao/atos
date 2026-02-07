package br.com.atos.app.exception;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExcepetionHandler {

    private static final Logger log =
        LoggerFactory.getLogger(GlobalExcepetionHandler.class);

    
    
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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest request){

        String logId = UUID.randomUUID().toString();

        log.warn(
            "logId={} | path={} | status={} | message={}",
            logId,
            request.getRequestURI(),
            404,
            ex.getMessage(),
            ex
        );

        ApiError error = new ApiError(
            404,
            "Bussines Error",
            ex.getMessage(),
            logId,
            request.getRequestURI(),
            LocalDateTime.now()
        );

        return ResponseEntity.status(404).body(error);
    }
}
