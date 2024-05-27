package de.ait.pool.service;


import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartServiceTest {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartProductRepository cartProductRepository;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        cartProductRepository = mock(CartProductRepository.class);
        cartService = new CartService(cartRepository, productRepository, cartProductRepository);
    }

    @Test
    @DisplayName("Test getCartProductById method with existing cart product")
    void testGetCartProductByIdExistingCartProduct() {
        // Arrange
        Long cartId = 1L;
        Long cartProductId = 1L;
        CartProduct cartProduct = createCartProduct(cartProductId, cartId);

        when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));

        // Act
        CartProductDto result = cartService.getCartProductById(cartId, cartProductId);

        // Assert
        assertNotNull(result);
        assertEquals(cartProduct.getId(), result.getId());
        assertEquals(cartProduct.getCart().getId(), result.getCartId());
        assertEquals(cartProduct.getProduct().getId(), result.getProductId());
        assertEquals(cartProduct.getQuantity(), result.getQuantity());
        assertEquals(cartProduct.getProduct().getTitle(), result.getProductName());
    }

    @Test
    @DisplayName("Test getCartProductById method with non-existing cart product")
    void testGetCartProductByIdNonExistingCartProduct() {
        // Arrange
        Long cartId = 1L;
        Long cartProductId = 1L;

        when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestException.class, () -> cartService.getCartProductById(cartId, cartProductId));
    }

    @Test
    @DisplayName("Test getCartProductById method with cart product not belonging to specified cart")
    void testGetCartProductByIdCartProductNotBelongingToSpecifiedCart() {
        // Arrange
        Long cartId = 1L;
        Long cartProductId = 1L;
        Long anotherCartId = 2L;

        CartProduct cartProduct = createCartProduct(cartProductId, anotherCartId);

        when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));

        // Act & Assert
        assertThrows(RestException.class, () -> cartService.getCartProductById(cartId, cartProductId));
    }

    @Test
    @DisplayName("Test addProductToCart method")
    void testAddProductToCart() {
        // Arrange
        Long cartId = 1L;
        Long productId = 1L;
        AddProductToCartDto addProductToCartDto = AddProductToCartDto.builder()
                .productId(productId)
                .quantity(2)
                .build();

        Cart cart = createCart(cartId);
        Product product = createProduct(productId, "Product", 10.00, "Category");

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartProductRepository.findByCartIdAndProductId(cartId, productId)).thenReturn(null);

        // Act
        CartProductDto result = cartService.addProductToCart(cartId, addProductToCartDto);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(cartId, result.getCartId());
        assertEquals(addProductToCartDto.getQuantity(), result.getQuantity());
        assertEquals(product.getTitle(), result.getProductName());
    }

    // Helper method to create a CartProduct instance
    private CartProduct createCartProduct(Long cartProductId, Long cartId) {
        Cart cart = createCart(cartId);
        Product product = createProduct(cartProductId, "Product", 10.00, "Category");
        return CartProduct.builder()
                .id(cartProductId)
                .cart(cart)
                .product(product)
                .quantity(1)
                .build();
    }

    // Helper method to create a Cart instance
    private Cart createCart(Long cartId) {
        return Cart.builder()
                .id(cartId)
                .user(null)
                .build();
    }

    // Helper method to create a Product instance
    private Product createProduct(Long id, String title, double price, String category) {
        return Product.builder()
                .id(id)
                .title(title)
                .price(BigDecimal.valueOf(price))
                .category(category)
                .build();
    }
}