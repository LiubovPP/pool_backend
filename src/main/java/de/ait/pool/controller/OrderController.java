package de.ait.pool.controller;

import de.ait.pool.controller.api.OrderApi;
import de.ait.pool.dto.orderDto.NewOrderDto;
import de.ait.pool.dto.orderDto.OrderDto;
import de.ait.pool.models.order.Order;
import de.ait.pool.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<OrderDto> getById(Long id) {
        return orderService.getById(id).map(OrderDto::from);
    }

    @Override
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @Override
    public OrderDto createOrder(NewOrderDto newOrderDto) {
        return orderService.createOrder(newOrderDto);
    }

    @Override
    public OrderDto updateOrder(Long id, NewOrderDto newOrderDto) {
        return orderService.updateOrder(id, newOrderDto);
    }

    @Override
    public OrderDto deleteOrder(Long id) {
        return orderService.deleteOrder(id);
    }
}