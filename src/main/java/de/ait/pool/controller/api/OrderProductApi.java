package de.ait.pool.controller.api;

import de.ait.pool.dto.orderDto.OrderProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tags(
        @Tag(name = "Order Products")
)
@RequestMapping("/api/order-products")

public interface OrderProductApi {
    @Operation(summary = "Получить продукты заказа по ID заказа", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @GetMapping("/by-order/{orderId}")
    List<OrderProductDto> getOrderProductsByOrderId(@PathVariable Long orderId);

    @Operation(summary = "Получить продукты заказа по ID продукта", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @GetMapping("/by-product/{productId}")
    List<OrderProductDto> getOrderProductsByProductId(@PathVariable Long productId);

    @Operation(summary = "Создать продукт заказа", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderProductDto createOrderProduct(@RequestBody @Valid OrderProductDto orderProductDto);

    @Operation(summary = "Обновить продукт заказа", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @PutMapping("/{id}")
    OrderProductDto updateOrderProduct(@PathVariable Long id, @RequestBody @Valid OrderProductDto orderProductDto);

    @Operation(summary = "Удалить продукт заказа", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @DeleteMapping("/{id}")
    OrderProductDto deleteOrderProduct(@PathVariable Long id);
}
