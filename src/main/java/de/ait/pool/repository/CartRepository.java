package de.ait.pool.repository;

import de.ait.pool.models.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
