package de.ait.pool.dto.orderDto;

import de.ait.pool.models.order.Order;
import de.ait.pool.models.order.OrderProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OrderProduct", description = "Информация о товаре в заказе")
public class OrderProductDto {
    @Schema(description = "Идентификатор товара в заказе")
    private Long id;

    @Schema(description = "Идентификатор заказа")
    private Long orderId;

    @Schema(description = "Идентификатор продукта")
    private Long productId;

    @Schema(description = "Количество товара")
    private int quantity;



    public static OrderProductDto from(OrderProduct orderProduct) {
        return OrderProductDto.builder()
                .id(orderProduct.getId())
                .productId(orderProduct.getProductId())
                .quantity(orderProduct.getQuantity())
                .orderId(orderProduct.getOrder() != null ? orderProduct.getOrder().getId() : null)
                .build();
    }

    public static List<OrderProductDto> from(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(OrderProductDto::from)
                .collect(Collectors.toList());
    }
    public OrderProduct toEntity(Order order) {
        return OrderProduct.builder()
                .productId(this.productId)
                .quantity(this.quantity)
                .order(order)
                .build();
    }
}
