package de.ait.pool.dto.orderDto;

import de.ait.pool.models.order.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

    @Schema(description = "Идентификатор пользователя, сделавшего заказ")
    private Long userId;

    @Schema(description = "Сумма заказа")
    private BigDecimal summa;

    @Schema(description = "Количество товаров в заказе")
    private int itemsCount;

    @Schema(description = "Дата заказа")
    private Date date;

    @Schema(description = "Список товаров в заказе")
    private List<OrderProductDto> products;


    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .summa(order.getSumma())
                .itemsCount(order.getItemsCount())
                .date(order.getDate())
                .build();
    }

    public static List<OrderDto> from(List<Order> orders) {
        return orders.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }
}
