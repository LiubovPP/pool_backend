package de.ait.pool.service;

import de.ait.pool.dto.orderDto.NewOrderDto;
import de.ait.pool.dto.orderDto.OrderDto;
import de.ait.pool.dto.orderDto.OrderProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.order.Order;
import de.ait.pool.repository.OrderProductRepository;
import de.ait.pool.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public OrderDto createOrder(NewOrderDto newOrderDto) {
        Order order = Order.builder()
                .userId(newOrderDto.getUserId())
                .summa(newOrderDto.getSumma())
                .itemsCount(newOrderDto.getItemsCount())
                .date(newOrderDto.getDate())
                .build();
        return OrderDto.from(orderRepository.save(order));
    }

    public OrderDto updateOrder(Long id, NewOrderDto newOrderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setUserId(newOrderDto.getUserId());
        order.setSumma(newOrderDto.getSumma());
        order.setItemsCount(newOrderDto.getItemsCount());
        order.setDate(newOrderDto.getDate());
        return OrderDto.from(orderRepository.save(order));
    }

    public OrderDto deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
        return OrderDto.from(order);
    }
}
