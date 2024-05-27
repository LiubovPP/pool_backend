package de.ait.pool.service;

import de.ait.pool.dto.productDto.NewProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    @DisplayName("Test findAll method")
    void testFindAll() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct(1L, "Product 1", new BigDecimal("10.00"), "Category 1"));
        productList.add(createProduct(2L, "Product 2", new BigDecimal("20.00"), "Category 2"));

        when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(productList, result);
    }

    @Test
    @DisplayName("Test findById method with existing product")
    void testFindByIdExistingProduct() {
        // Arrange
        Long productId = 1L;
        Product product = createProduct(productId, "Product", new BigDecimal("10.00"), "Category");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    @DisplayName("Test findById method with non-existing product")
    void testFindByIdNonExistingProduct() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestException.class, () -> productService.findById(productId));
    }

    @Test
    @DisplayName("Test createProduct method")
    void testCreateProduct() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("New Product")
                .price(new BigDecimal("15.00"))
                .category("Category")
                .build();

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProductDto result = productService.createProduct(newProductDto);

        // Assert
        assertNotNull(result);
        assertEquals(newProductDto.getTitle(), result.getTitle());
        assertEquals(newProductDto.getPrice(), result.getPrice());
        assertEquals(newProductDto.getCategory(), result.getCategory());
    }

    @Test
    @DisplayName("Test updateProduct method")
    void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        NewProductDto newProductDto = NewProductDto.builder()
                .title("Updated Product")
                .price(new BigDecimal("20.00"))
                .category("Category")
                .build();

        Product existingProduct = createProduct(productId, "Product", new BigDecimal("10.00"), "Category");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProductDto result = productService.updateProduct(productId, newProductDto);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals(newProductDto.getTitle(), result.getTitle());
        assertEquals(newProductDto.getPrice(), result.getPrice());
        assertEquals(newProductDto.getCategory(), result.getCategory());
    }

    @Test
    @DisplayName("Test deleteProduct method with existing product")
    void testDeleteProductExistingProduct() {
        // Arrange
        Long productId = 1L;
        Product productToDelete = createProduct(productId, "Product", new BigDecimal("10.00"), "Category");

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productToDelete));

        // Act
        ProductDto result = productService.deleteProduct(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    @DisplayName("Test deleteProduct method with non-existing product")
    void testDeleteProductNonExistingProduct() {
        // Arrange
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertThrows(RestException.class, () -> productService.deleteProduct(productId));
    }

    // Helper method to create a Product instance
    private Product createProduct(Long id, String title, BigDecimal price, String category) {
        return Product.builder()
                .id(id)
                .title(title)
                .price(price)
                .category(category)
                .build();
    }

}