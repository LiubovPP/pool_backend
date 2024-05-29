package de.ait.pool.dto.orderDto;

import de.ait.pool.models.order.Order;
import de.ait.pool.models.order.OrderProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
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

    @Schema(description = "Идентификатор товара")
    private Long productId;

    @Schema(description = "Количество")
    private int quantity;

    @Schema(description = "Цена")
    private BigDecimal price;

    public static OrderProductDto from(OrderProduct orderProduct) {
        return OrderProductDto.builder()
                .id(orderProduct.getId())
                .orderId(orderProduct.getOrder().getId())
                .productId(orderProduct.getProduct().getId())
                .quantity(orderProduct.getQuantity())
                .price(orderProduct.getPrice())
                .build();
    }

    public static List<OrderProductDto> from(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(OrderProductDto::from)
                .collect(Collectors.toList());
    }
}
