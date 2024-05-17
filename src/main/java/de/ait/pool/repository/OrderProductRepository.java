package de.ait.pool.repository;

import de.ait.pool.models.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findByProductId(Long productId);
    List<OrderProduct> findByOrderId(Long orderId);
}
