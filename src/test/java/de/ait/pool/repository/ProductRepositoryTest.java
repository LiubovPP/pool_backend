package de.ait.pool.repository;

import de.ait.pool.models.Product;
import de.ait.pool.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Проверка наличия продукта по ID")
    void testExistsById() {
        // Arrange
        Long productId = 1L;
        Product product = new Product(productId, "TestProduct", BigDecimal.valueOf(10.0), "TestCategory");
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        boolean exists = productRepository.existsById(productId);

        // Assert
        assertEquals(true, exists);
        verify(productRepository, times(1)).existsById(productId);
    }
}