package de.ait.pool.repository;

import de.ait.pool.models.cart.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByCart_IdAndProduct_Id(Long cartId, Long productId);

}
