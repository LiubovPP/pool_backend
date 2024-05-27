package de.ait.pool.dto.productDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for AddProductToCartDto class")
class AddProductToCartDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Test AddProductToCartDto builder")
    void testBuilder() {
        // Arrange
        AddProductToCartDto dto = AddProductToCartDto.builder()
                .productId(1L)
                .quantity(2)
                .build();

        // Act & Assert
        assertEquals(1L, dto.getProductId());
        assertEquals(2, dto.getQuantity());
    }

    @Test
    @DisplayName("Test validation with valid data")
    void testValidationWithValidData() {
        // Arrange
        AddProductToCartDto dto = AddProductToCartDto.builder()
                .productId(1L)
                .quantity(2)
                .build();

        // Act
        Set<ConstraintViolation<AddProductToCartDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    @DisplayName("Test validation with null productId")
    void testValidationWithNullProductId() {
        // Arrange
        AddProductToCartDto dto = AddProductToCartDto.builder()
                .productId(null)
                .quantity(2)
                .build();

        // Act
        Set<ConstraintViolation<AddProductToCartDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> "Product ID is mandatory".equals(v.getMessage())));
    }

    @Test
    @DisplayName("Validation check with non-positive product quantity")
    void testValidationWithNonPositiveQuantity() {
        // Preparation
        AddProductToCartDto dto = AddProductToCartDto.builder()
                .productId(1L)
                .quantity(0) // Non-positive quantity
                .build();

        // Action
        Set<ConstraintViolation<AddProductToCartDto>> violations = validator.validate(dto);

        // Assertion
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> "Product quantity must be positive".equals(v.getMessage())));
    }

    @Test
    @DisplayName("Test validation with negative quantity")
    void testValidationWithNegativeQuantity() {
        // Arrange
        AddProductToCartDto dto = AddProductToCartDto.builder()
                .productId(1L)
                .quantity(-2)
                .build();

        // Act
        Set<ConstraintViolation<AddProductToCartDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Quantity must be greater than or equal to 0")));
    }
}