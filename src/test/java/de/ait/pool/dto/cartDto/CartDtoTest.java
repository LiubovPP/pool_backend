package de.ait.pool.dto.cartDto;

import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Cart class")
class CartDtoTest {


    @Test
    @DisplayName("Test Cart builder")
    public void testBuilder() {
        Cart cart = Cart.builder()
                .id(1L)
                .user(new User())
                .cartProducts(new HashSet<>())
                .build();

        assertEquals(1L, cart.getId());
        assertNotNull(cart.getUser());
        assertNotNull(cart.getCartProducts());
        assertEquals(0, cart.getCartProducts().size());
    }

    @Test
    @DisplayName("Test Cart getter and setter")
    public void testSetterGetter() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(new User());
        cart.setCartProducts(new HashSet<>());

        assertEquals(1L, cart.getId());
        assertNotNull(cart.getUser());
        assertNotNull(cart.getCartProducts());
        assertEquals(0, cart.getCartProducts().size());
    }

    @Test
    @DisplayName("Test Cart toString method")
    public void testToString() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCartProducts(new HashSet<>());

        String expected = "Cart: ID - 1, products - 0 items";
        assertEquals(expected, cart.toString());
    }
}