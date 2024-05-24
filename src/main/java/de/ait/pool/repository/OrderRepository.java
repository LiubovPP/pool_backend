package de.ait.pool.repository;

import de.ait.pool.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Поиск всех заказов по userId
    List<Order> findByUserId(Long userId);

    // Поиск заказов по userId и сумме больше заданной
    List<Order> findByUserIdAndSummaGreaterThan(Long userId, BigDecimal summa);

    // Поиск заказов по диапазону дат
    List<Order> findByDateBetween(Date startDate, Date endDate);

    // Поиск заказов с суммой больше заданной
    List<Order> findBySummaGreaterThan(BigDecimal summa);

   /* // Использование JPQL для поиска заказов с определенным количеством товаров
    @Query("SELECT o FROM Order o WHERE o.itemsCount = :itemsCount")
    List<Order> findByItemsCount(int itemsCount);

    // Использование JPQL для поиска заказов по категории продукта
    @Query("SELECT o FROM Order o JOIN o.products p WHERE p.productId = :productId")
    List<Order> findByProductId(Long productId);*/
}
