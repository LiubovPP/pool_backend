package de.ait.pool.repository;

import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CartRepositoryTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartRepositoryTest cartRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test saving a cart")
    void testSaveCart() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .hashPassword("password")
                .phoneNumber("1234567890")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart savedCart = cartRepository.save(cart);

        assertThat(savedCart).isNotNull();
        assertThat(savedCart.getId()).isEqualTo(1L);
        assertThat(savedCart.getUser()).isEqualTo(user);

        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    @DisplayName("Test finding a cart by ID")
    void testFindCartById() {
        User user = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .hashPassword("password")
                .phoneNumber("0987654321")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

        Optional<Cart> foundCart = cartRepository.findById(1L);

        assertThat(foundCart).isPresent();
        assertThat(foundCart.get().getUser()).isEqualTo(user);

        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test deleting a cart")
    void testDeleteCart() {
        User user = User.builder()
                .id(1L)
                .firstName("Alex")
                .lastName("Smith")
                .email("alex.smith@example.com")
                .hashPassword("password")
                .phoneNumber("1122334455")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        doNothing().when(cartRepository).delete(any(Cart.class));

        cartRepository.delete(cart);

        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    @DisplayName("Test cart with cart products")
    void testCartWithCartProducts() {
        User user = User.builder()
                .id(1L)
                .firstName("Sam")
                .lastName("Brown")
                .email("sam.brown@example.com")
                .hashPassword("password")
                .phoneNumber("1231231234")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();

        Product product1 = Product.builder()
                .id(1L)
                .title("Product 1")
                .price(new BigDecimal("10.00"))
                .category("Category 1")
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .title("Product 2")
                .price(new BigDecimal("20.00"))
                .category("Category 2")
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .build();

        CartProduct cartProduct1 = CartProduct.builder()
                .id(1L)
                .cart(cart)
                .product(product1)
                .quantity(2)
                .build();

        CartProduct cartProduct2 = CartProduct.builder()
                .id(2L)
                .cart(cart)
                .product(product2)
                .quantity(3)
                .build();

        Set<CartProduct> cartProducts = new HashSet<>();
        cartProducts.add(cartProduct1);
        cartProducts.add(cartProduct2);

        cart.setCartProducts(cartProducts);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(productRepository.save(any(Product.class))).thenReturn(product1, product2);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart savedCart = cartRepository.save(cart);

        assertThat(savedCart).isNotNull();
        assertThat(savedCart.getCartProducts()).hasSize(2);

        verify(cartRepository, times(1)).save(cart);
    }
}