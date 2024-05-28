package de.ait.pool.repository;



import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    //    CartProduct findByCart_IdAndProduct_Id(Long cartId, Long productId);
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);

    Set<CartProduct> findByCartId(Long cartId);

    Set<CartProduct> findByCart(Cart cart);
}
