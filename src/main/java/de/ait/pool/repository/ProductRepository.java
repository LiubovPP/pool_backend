package de.ait.pool.repository;

import de.ait.pool.dto.productDto.ProductDto;
import de.ait.pool.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(Long id);
}
