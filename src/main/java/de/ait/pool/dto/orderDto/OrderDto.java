package de.ait.pool.dto.orderDto;

import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.order.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Order", description = "Информация о заказе")
public class OrderDto {
    @Schema(description = "Идентификатор заказа")
    private Long id;

    @Schema(description = "Дата заказа")
    private LocalDateTime date;

    @Schema(description = "Сумма заказа")
    private BigDecimal summa;

    @Schema(description = "Id пользователя")
    private Long userId;

    @Schema(description = "Список товаров в заказе")
    private Set<OrderProductDto> products;



    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .summa(order.getSumma())
                .userId(order.getUser().getId())
                .products(OrderProductDto.from(order.getOrderProducts()))
                .build();
    }

    public static Set<OrderDto> from(List<Order> orders) {
        return orders.stream()
                .map(OrderDto::from)
                .collect(Collectors.toSet());
    }
}
