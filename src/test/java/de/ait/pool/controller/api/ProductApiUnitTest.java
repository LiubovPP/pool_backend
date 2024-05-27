package de.ait.pool.controller.api;

import de.ait.pool.controller.ProductController;
import de.ait.pool.dto.productDto.NewProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import de.ait.pool.models.Product;
import de.ait.pool.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Product Controller Tests")
class ProductApiUnitTest {


    ProductService productService = mock(ProductService.class);
    ProductController productController = new ProductController(productService);

    @Test
    @DisplayName("Test getting product by ID")
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .title("Test Product")
                .price(BigDecimal.valueOf(19.99))
                .category("Test Category")
                .build();
        when(productService.findById(any())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            if (id.equals(productId)) {
                return Optional.of(product);
            } else {
                return Optional.empty();
            }
        });

        // Act
        Optional<ProductDto> result = productController.getById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());
        assertEquals(product.getTitle(), result.get().getTitle());
        assertEquals(product.getPrice(), result.get().getPrice());
        assertEquals(product.getCategory(), result.get().getCategory());
        verify(productService, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Test getting all products")
    void testGetAllProducts() {

        // Arrange
        List<Product> productList = List.of(
                Product.builder()
                        .id(1L)
                        .title("Product 1")
                        .price(BigDecimal.valueOf(10.99))
                        .category("Category 1")
                        .build(),
                Product.builder()
                        .id(2L)
                        .title("Product 2")
                        .price(BigDecimal.valueOf(15.99))
                        .category("Category 2")
                        .build()
        );

        when(productService.findAll()).thenReturn(productList);

        List<ProductDto> expectedProductDtoList = productList.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());

        // Act
        List<ProductDto> result = productController.getProducts();

        // Assert
        assertEquals(expectedProductDtoList, result);
        verify(productService, times(1)).findAll();
    }

    @Test
    @DisplayName("Test creating a new product")
    void testCreateProduct() {
        // Arrange
        NewProductDto newProductDto = NewProductDto.builder()
                .title("New Product")
                .price(BigDecimal.valueOf(29.99))
                .category("New Category")
                .build();
        ProductDto createdProductDto = ProductDto.builder()
                .id(1L)
                .title(newProductDto.getTitle())
                .price(newProductDto.getPrice())
                .category(newProductDto.getCategory())
                .build();
        when(productService.createProduct(newProductDto)).thenReturn(createdProductDto);

        // Act
        ProductDto result = productController.createProduct(newProductDto);

        // Assert
        assertEquals(createdProductDto, result);
        verify(productService, times(1)).createProduct(newProductDto);
   }
    @Test
    @DisplayName("Test updating a product")
    void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        NewProductDto updatedProductDto = NewProductDto.builder()
                .title("Updated Product")
                .price(BigDecimal.valueOf(39.99))
                .category("Updated Category")
                .build();
        ProductDto updatedProduct = ProductDto.builder()
                .id(productId)
                .title(updatedProductDto.getTitle())
                .price(updatedProductDto.getPrice())
                .category(updatedProductDto.getCategory())
                .build();
        when(productService.updateProduct(productId, updatedProductDto)).thenReturn(updatedProduct);

        // Act
        ProductDto result = productController.updateProduct(productId, updatedProductDto);

        // Assert
        assertEquals(updatedProduct, result);
        verify(productService, times(1)).updateProduct(productId, updatedProductDto);
    }

    @Test
    @DisplayName("Test deleting a product")
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        ProductDto deletedProduct = ProductDto.builder()
                .id(productId)
                .title("Deleted Product")
                .price(BigDecimal.valueOf(29.99))
                .category("Deleted Category")
                .build();
        when(productService.deleteProduct(productId)).thenReturn(deletedProduct);

        // Act
        ProductDto result = productController.deleteProduct(productId);

        // Assert
        assertEquals(deletedProduct, result);
        verify(productService, times(1)).deleteProduct(productId);
    }

}