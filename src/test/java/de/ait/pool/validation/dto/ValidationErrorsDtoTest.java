package de.ait.pool.validation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationErrorsDtoTest {

    @Test
    @DisplayName("Test constructor and getters of ValidationErrorsDto")
    void testValidationErrorsDto() {
        // Arrange
        List<ValidationErrorDto> errors = new ArrayList<>();
        errors.add(new ValidationErrorDto("field1", "value1", "message1"));
        errors.add(new ValidationErrorDto("field2", "value2", "message2"));

        // Act
        ValidationErrorsDto errorsDto = new ValidationErrorsDto(errors);

        // Assert
        assertThat(errorsDto).isNotNull();
        assertThat(errorsDto.getErrors()).isEqualTo(errors);
    }

    @Test
    @DisplayName("Test setters of ValidationErrorsDto")
    void testSetters() {
        // Arrange
        ValidationErrorsDto errorsDto = new ValidationErrorsDto();
        List<ValidationErrorDto> errors = new ArrayList<>();
        errors.add(new ValidationErrorDto("field1", "value1", "message1"));
        errors.add(new ValidationErrorDto("field2", "value2", "message2"));

        // Act
        errorsDto.setErrors(errors);

        // Assert
        assertThat(errorsDto.getErrors()).isEqualTo(errors);
    }
}