package de.ait.pool.repository;

import de.ait.pool.models.cart.CartProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    Optional<CartProduct> findByCart_IdAndProduct_Id(Long cartId, Long productId);

}
