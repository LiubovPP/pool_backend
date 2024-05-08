package de.ait.pool.exceptions;


import de.ait.pool.dto.StandardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 обработчик ошибок
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<StandardResponseDto> handleRestException(RestException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(StandardResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

}
