package de.ait.pool.dto.cartDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for UpdateCartProductDto class")
class UpdateCartProductDtoTest {

    @Test
    @DisplayName("Test UpdateCartProductDto builder")
    public void testBuilder() {
        UpdateCartProductDto updateCartProductDto = UpdateCartProductDto.builder()
                .quantity(2)
                .build();

        assertEquals(2, updateCartProductDto.getQuantity());
    }

    @Test
    @DisplayName("Test UpdateCartProductDto getters and setters")
    public void testGettersAndSetters() {
        UpdateCartProductDto updateCartProductDto = new UpdateCartProductDto();
        updateCartProductDto.setQuantity(3);

        assertEquals(3, updateCartProductDto.getQuantity());
    }
}