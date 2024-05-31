/*
package de.ait.pool.service;

import de.ait.pool.dto.cartDto.UpdateCartProductDto;

import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("CartProductService Tests")
class CartProductServiceTest {

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartProductService cartProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getCartProducts method")
    void testGetCartProducts() {
        // Arrange
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        Set<CartProduct> cartProducts = new HashSet<>();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartProductRepository.findByCartIdAndProductId(cartId, null)).thenReturn(new CartProduct());

         // Act
        Set<CartProductDto> result = cartProductService.getCartProducts(cartId);

         // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test getCartProducts2 method")
    void testGetCartProducts2() {
        // Arrange
        Long cartId = 1L;
        Long userId = 1L;
        User user = new User(userId, "John", "Doe", "john.doe@example.com", "hashedPassword", "1234567890", User.Role.USER, User.State.CONFIRMED, null);
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUser(user);
        user.setCart(cart);

        Product product1 = new Product(1L, "Product 1", BigDecimal.valueOf(10.00), "Category 1");
        Product product2 = new Product(2L, "Product 2", BigDecimal.valueOf(20.00), "Category 2");

        CartProduct cartProduct1 = new CartProduct();
        cartProduct1.setId(1L);
        cartProduct1.setCart(cart);
        cartProduct1.setProduct(product1);
        cartProduct1.setQuantity(1);

        CartProduct cartProduct2 = new CartProduct();
        cartProduct2.setId(2L);
        cartProduct2.setCart(cart);
        cartProduct2.setProduct(product2);
        cartProduct2.setQuantity(2);

        Set<CartProduct> cartProducts = new HashSet<>(Arrays.asList(cartProduct1, cartProduct2));
        cart.setCartProducts(cartProducts);

        System.out.println("Mocked result from findByCartIdAndProductId2: " + cartProducts);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartProductRepository.findByCartId(cartId)).thenReturn(cartProducts);

        // Act
        Set<CartProductDto> result = cartProductService.getCartProducts(cartId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    @Test
    @DisplayName("Test getCartProducts method with empty cart")
    void testGetCartProducts_EmptyCart() {
        // Arrange
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartProductRepository.findByCartId(cartId)).thenReturn(new HashSet<>());

        // Act
        Set<CartProductDto> result = cartProductService.getCartProducts(cartId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test getCartProducts method with non-empty cart")
    void testGetCartProducts_NonEmptyCart() {
        // Arrange
        Long cartId = 1L;
        Long userId = 1L;
        User user = new User(userId, "John", "Doe", "john.doe@example.com", "hashedPassword", "1234567890", User.Role.USER, User.State.CONFIRMED, null);
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUser(user);
        user.setCart(cart);

        Product product1 = new Product(1L, "Product 1", BigDecimal.valueOf(10.00), "Category 1");
        Product product2 = new Product(2L, "Product 2", BigDecimal.valueOf(20.00), "Category 2");

        CartProduct cartProduct1 = new CartProduct();
        cartProduct1.setId(1L);
        cartProduct1.setCart(cart);
        cartProduct1.setProduct(product1);
        cartProduct1.setQuantity(1);

        CartProduct cartProduct2 = new CartProduct();
        cartProduct2.setId(2L);
        cartProduct2.setCart(cart);
        cartProduct2.setProduct(product2);
        cartProduct2.setQuantity(2);

        cart.setCartProducts(new HashSet<>(Arrays.asList(cartProduct1, cartProduct2)));

        // Вывести cartId для отладки
        System.out.println("cartId: " + cartId);
        System.out.println("Mocked cartProducts: " + cart.getCartProducts());

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartProductRepository.findByCartId(cartId)).thenReturn(cart.getCartProducts());

        // Act
        Set<CartProductDto> result = cartProductService.getCartProducts(cartId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Test getCartProducts method with non-existing cart")
    void testGetCartProducts_NonExistingCart() {
        // Arrange
        Long cartId = 999L; // Non-existing cart
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestException.class, () -> cartProductService.getCartProducts(cartId));
    }

    @Test
    @DisplayName("Test updateCartProduct method")
    void testUpdateCartProduct() {
        // Arrange
        Long cartId = 1L;
        Long cartProductId = 1L;
        UpdateCartProductDto updateCartProductDto = new UpdateCartProductDto();
        updateCartProductDto.setQuantity(3);
        Cart cart = new Cart();
        cart.setId(cartId);
        Product product = new Product();
        product.setId(1L);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(cartProductId);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));

         // Act
        CartProductDto updatedCartProductDto = cartProductService.updateCartProduct(cartId, cartProductId, updateCartProductDto);

         // Assert
        assertNotNull(updatedCartProductDto);
        assertEquals(cartProductId, updatedCartProductDto.getId());
        assertEquals(cartId, updatedCartProductDto.getCartId());
        assertEquals(3, updatedCartProductDto.getQuantity());
    }

    @Test
    @DisplayName("Test deleteCartProduct method")
    void testDeleteCartProduct() {
        // Arrange
        Long cartId = 1L;
        Long cartProductId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        Product product = new Product();
        product.setId(1L);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(cartProductId);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        // Mock cart repository method
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        // Mock cart product repository method
        when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));

        // Act
        CartProductDto deletedCartProductDto = cartProductService.deleteCartProduct(cartId, cartProductId);

        // Assert
        assertNotNull(deletedCartProductDto);
        assertEquals(cartProductId, deletedCartProductDto.getId());
        assertEquals(cartId, deletedCartProductDto.getCartId());
    }
}*/
