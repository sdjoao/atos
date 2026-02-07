package br.com.atos.app.exception;

import java.time.LocalDateTime;

public record ApiError(
    int status,
    String error,
    String message,
    String logId,
    String path,
    LocalDateTime timestamp
) {}
