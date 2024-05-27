package de.ait.pool.dto.productDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for NewProductDto class")
class NewProductDtoTest {
    private final Validator validator;

    public NewProductDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    @DisplayName("Check valid NewProductDto")
    void testValidNewProductDto() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("TestProduct")
                .price(BigDecimal.valueOf(19.99))
                .category("TestCategory")
                .build();

        // Act
        Set<ConstraintViolation<NewProductDto>> violations = validator.validate(newProductDto);

        // Assert
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    @DisplayName("Check that blank title triggers validation error")
    void testTitleIsBlank() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("")
                .price(BigDecimal.valueOf(19.99))
                .category("TestCategory")
                .build();

        // Act
        Set<ConstraintViolation<NewProductDto>> violations = validator.validate(newProductDto);

        // Assert
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Название обязательно")));
    }

    @Test
    @DisplayName("Check that title exceeding max length triggers validation error")
    void testTitleExceedsMaxLength() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("ThisTitleIsWayTooLongAndShouldFailValidation")
                .price(BigDecimal.valueOf(19.99))
                .category("TestCategory")
                .build();

        // Act
        Set<ConstraintViolation<NewProductDto>> violations = validator.validate(newProductDto);

        // Assert
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Длина названия должна быть меньше или равна 20 символам")));
    }

    @Test
    @DisplayName("Check that null price triggers validation error")
    void testPriceIsNull() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("TestProduct")
                .price(null)
                .category("TestCategory")
                .build();

        // Act
        Set<ConstraintViolation<NewProductDto>> violations = validator.validate(newProductDto);

        // Assert
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Цена обязательна")));
    }

    @Test
    @DisplayName("Check that null category passes validation")
    void testValidCategoryIsNull() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("TestProduct")
                .price(BigDecimal.valueOf(19.99))
                .category(null)
                .build();

        // Act
        Set<ConstraintViolation<NewProductDto>> violations = validator.validate(newProductDto);

        // Assert
        assertTrue(violations.isEmpty(), "Expected no validation errors for null category");
    }

}