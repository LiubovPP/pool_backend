package de.ait.pool.validation.handler;
import de.ait.pool.validation.dto.ValidationErrorsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("ValidationExceptionHandler Tests")
class ValidationExceptionHandlerTest {

    @InjectMocks
    private ValidationExceptionHandler validationExceptionHandler;

     @Mock
      private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test handleValidationException method with one validation error")
    void testHandleValidationExceptionWithOneError() {
        // Arrange
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(createErrorsList("fieldName", "Field must not be empty"));

        // Act
        ResponseEntity<ValidationErrorsDto> responseEntity = validationExceptionHandler.handleValidationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getErrors().size());
        assertEquals("fieldName", responseEntity.getBody().getErrors().get(0).getField());
        assertEquals("Field must not be empty", responseEntity.getBody().getErrors().get(0).getMessage());
    }

    @Test
    @DisplayName("Test handleValidationException method with multiple validation errors")
    void testHandleValidationExceptionWithMultipleErrors() {
        // Arrange
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("objectName", "field1", "Error 1"));
        errors.add(new FieldError("objectName", "field2", "Error 2"));

        BindingResult bindingResult = new DirectFieldBindingResult(new Object(), "objectName");
        errors.forEach(bindingResult::addError);

        // Check if errors are added correctly
        assertEquals(2, bindingResult.getErrorCount(), "Errors are not added correctly to BindingResult");

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<ValidationErrorsDto> responseEntity = validationExceptionHandler.handleValidationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().getErrors().size());
        assertEquals("field1", responseEntity.getBody().getErrors().get(0).getField());
        assertEquals("Error 1", responseEntity.getBody().getErrors().get(0).getMessage());
        assertEquals("field2", responseEntity.getBody().getErrors().get(1).getField());
        assertEquals("Error 2", responseEntity.getBody().getErrors().get(1).getMessage());
    }

    private List<ObjectError> createErrorsList(String field, String message) {
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("objectName", field, message));
        return errors;
    }

}