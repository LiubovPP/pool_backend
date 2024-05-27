package de.ait.pool.validation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationErrorDtoTest {
    @Test
    @DisplayName("Test constructor and getters of ValidationErrorDto")
    void testValidationErrorDto() {
        // Arrange
        String field = "price";
        String rejectedValue = "1000.0";
        String message = "must be less than or equal to 200";

        // Act
        ValidationErrorDto errorDto = new ValidationErrorDto(field, rejectedValue, message);

        // Assert
        assertThat(errorDto).isNotNull();
        assertThat(errorDto.getField()).isEqualTo(field);
        assertThat(errorDto.getRejectedValue()).isEqualTo(rejectedValue);
        assertThat(errorDto.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test setters of ValidationErrorDto")
    void testSetters() {
        // Arrange
        ValidationErrorDto errorDto = new ValidationErrorDto();
        String field = "name";
        String rejectedValue = "John";
        String message = "must not be empty";

        // Act
        errorDto.setField(field);
        errorDto.setRejectedValue(rejectedValue);
        errorDto.setMessage(message);

        // Assert
        assertThat(errorDto.getField()).isEqualTo(field);
        assertThat(errorDto.getRejectedValue()).isEqualTo(rejectedValue);
        assertThat(errorDto.getMessage()).isEqualTo(message);
    }

}