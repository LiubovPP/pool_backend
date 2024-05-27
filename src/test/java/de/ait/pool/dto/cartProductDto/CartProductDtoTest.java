package de.ait.pool.dto.cartProductDto;

import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.models.Product;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for CartProductDto class")
class CartProductDtoTest {

    @Test
    @DisplayName("Test CartProductDto builder")
    public void testBuilder() {
        CartProductDto cartProductDto = CartProductDto.builder()
                .id(1L)
                .cartId(1L)
                .productId(1L)
                .productName("Test Product")
                .quantity(2)
                .build();

        assertEquals(1L, cartProductDto.getId());
        assertEquals(1L, cartProductDto.getCartId());
        assertEquals(1L, cartProductDto.getProductId());
        assertEquals("Test Product", cartProductDto.getProductName());
        assertEquals(2, cartProductDto.getQuantity());
    }

    @Test
    @DisplayName("Test CartProductDto fromCartProduct method")
    public void testFromCartProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");

        Cart cart = new Cart();
        cart.setId(1L);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(2);

        CartProductDto cartProductDto = CartProductDto.fromCartProduct(cartProduct);

        assertEquals(1L, cartProductDto.getId());
        assertEquals(1L, cartProductDto.getCartId());
        assertEquals(1L, cartProductDto.getProductId());
        assertEquals("Test Product", cartProductDto.getProductName());
        assertEquals(2, cartProductDto.getQuantity());
    }
}