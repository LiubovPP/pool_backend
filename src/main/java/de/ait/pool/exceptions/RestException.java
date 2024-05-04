package de.ait.pool.exceptions;

import org.springframework.http.HttpStatus;

/**
 чтоб можно выбрасывать ошибки всесте с текстом и статусом
 */
public class RestException extends RuntimeException {
    private final HttpStatus status;

    public RestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
