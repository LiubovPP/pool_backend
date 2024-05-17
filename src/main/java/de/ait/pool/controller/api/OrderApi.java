package de.ait.pool.controller.api;

import de.ait.pool.dto.orderDto.NewOrderDto;
import de.ait.pool.dto.orderDto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tags(
        @Tag(name = "Orders")
)
@RequestMapping("/api/orders")
public interface OrderApi {
    @Operation(summary = "Получить заказ по ID", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @GetMapping("/{id}")
    Optional<OrderDto> getById(@PathVariable Long id);

    @Operation(summary = "Получить все заказы", description = "Доступно всем. По умолчанию роль - USER")
    @GetMapping
    List<OrderDto> getOrders();

    @Operation(summary = "Создание нового заказа", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderDto createOrder(@RequestBody @Valid NewOrderDto newOrderDto);

    @Operation(summary = "Обновить заказ", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @PutMapping("/{id}")
    OrderDto updateOrder(@PathVariable Long id, @RequestBody @Valid NewOrderDto newOrderDto);

    @Operation(summary = "Удалить заказ", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @DeleteMapping("/{id}")
    OrderDto deleteOrder(@PathVariable Long id);
}
