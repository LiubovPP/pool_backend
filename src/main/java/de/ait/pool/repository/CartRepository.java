package de.ait.pool.repository;

import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Set<CartProductDto> findByUser(User currentUser);
}
