package de.ait.pool.controller;

import de.ait.pool.controller.api.OrderApi;
import de.ait.pool.dto.orderDto.NewOrderDto;
import de.ait.pool.dto.orderDto.OrderDto;
import de.ait.pool.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;

    @Override
    public Optional<OrderDto> getById(@PathVariable Long id) {
        return orderService.findById(id).map(OrderDto::from);
    }

    @Override
    public List<OrderDto> getOrders() {
        return OrderDto.from(orderService.findAll());
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody @Valid NewOrderDto newOrderDto) {
        return orderService.createOrder(newOrderDto);
    }

    @Override
    public OrderDto updateOrder(@PathVariable Long id, @RequestBody @Valid NewOrderDto newOrderDto) {
        return orderService.updateOrder(id, newOrderDto);
    }

    @Override
    public OrderDto deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}
