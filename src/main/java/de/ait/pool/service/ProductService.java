package de.ait.pool.service;

import de.ait.pool.dto.productDto.NewProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import de.ait.pool.exceptions.ProductNotFoundException;
import de.ait.pool.models.Product;
import de.ait.pool.repository.ProductRepository;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductDto createProduct(NewProductDto newProductDto) {

        // Basic validation (can be improved)
        if (newProductDto.getTitle() == null || newProductDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (newProductDto.getPrice() == null || newProductDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        // Create new Product from NewProductDto
        Product product = new Product();
        product.setTitle(newProductDto.getTitle());
        product.setPrice(newProductDto.getPrice());
        product.setCategory(newProductDto.getCategory());

        // Save product and return ProductDto
        Product savedProduct = productRepository.save(product);
        return ProductDto.from(savedProduct);
    }

    public ProductDto updateProduct(Long id, NewProductDto newProductDto) {

        // Basic validation (can be improved)
        if (newProductDto.getTitle() == null || newProductDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (newProductDto.getPrice() == null || newProductDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        // Find product by id
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Update product fields
        product.setTitle(newProductDto.getTitle());
        product.setPrice(newProductDto.getPrice());
        product.setCategory(newProductDto.getCategory());

        // Save updated product and return ProductDto
        Product savedProduct = productRepository.save(product);
        return ProductDto.from(savedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}