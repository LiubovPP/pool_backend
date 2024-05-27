package de.ait.pool.repository;


import de.ait.pool.models.Product;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
public class CartProductRepositoryTest {
    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartProductRepositoryTest cartProductRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test finding a CartProduct by cart ID and product ID")
    void testFindByCartIdAndProductId() {
        Cart cart = Cart.builder()
                .id(1L)
                .build();

        Product product = Product.builder()
                .id(1L)
                .title("Product 1")
                .price(new BigDecimal("10.00"))
                .category("Category 1")
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .id(1L)
                .cart(cart)
                .product(product)
                .quantity(2)
                .build();

        when(cartProductRepository.findByCartIdAndProductId(anyLong(), anyLong())).thenReturn(cartProduct);

        CartProduct foundCartProduct = cartProductRepository.findByCartIdAndProductId(1L, 1L);

        assertThat(foundCartProduct).isNotNull();
        assertThat(foundCartProduct.getId()).isEqualTo(1L);
        assertThat(foundCartProduct.getCart().getId()).isEqualTo(1L);
        assertThat(foundCartProduct.getProduct().getId()).isEqualTo(1L);

        verify(cartProductRepository, times(1)).findByCartIdAndProductId(1L, 1L);
    }

    @Test
    @DisplayName("Test saving a CartProduct")
    void testSaveCartProduct() {
        Cart cart = Cart.builder()
                .id(1L)
                .build();

        Product product = Product.builder()
                .id(1L)
                .title("Product 1")
                .price(new BigDecimal("10.00"))
                .category("Category 1")
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .id(1L)
                .cart(cart)
                .product(product)
                .quantity(2)
                .build();

        when(cartProductRepository.save(any(CartProduct.class))).thenReturn(cartProduct);

        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);

        assertThat(savedCartProduct).isNotNull();
        assertThat(savedCartProduct.getId()).isEqualTo(1L);
        assertThat(savedCartProduct.getCart().getId()).isEqualTo(1L);
        assertThat(savedCartProduct.getProduct().getId()).isEqualTo(1L);

        verify(cartProductRepository, times(1)).save(cartProduct);
    }

    @Test
    @DisplayName("Test deleting a CartProduct")
    void testDeleteCartProduct() {
        Cart cart = Cart.builder()
                .id(1L)
                .build();

        Product product = Product.builder()
                .id(1L)
                .title("Product 1")
                .price(new BigDecimal("10.00"))
                .category("Category 1")
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .id(1L)
                .cart(cart)
                .product(product)
                .quantity(2)
                .build();

        doNothing().when(cartProductRepository).delete(any(CartProduct.class));

        cartProductRepository.delete(cartProduct);

        verify(cartProductRepository, times(1)).delete(cartProduct);
    }


}
