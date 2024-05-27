package de.ait.pool.service;

import de.ait.pool.dto.orderDto.NewOrderDto;
import de.ait.pool.dto.orderDto.OrderDto;
import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.order.Order;
import de.ait.pool.models.order.OrderProduct;
import de.ait.pool.repository.OrderProductRepository;
import de.ait.pool.repository.OrderRepository;
import de.ait.pool.repository.ProductRepository;
import de.ait.pool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public OrderDto createOrder(NewOrderDto newOrderDto) {
        Product product = productRepository.findById(newOrderDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        User user = userRepository.findById(newOrderDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = Order.builder()
                .product(product)
                .user(user)
                .itemsCount(newOrderDto.getItemsCount())
                .date(newOrderDto.getDate())
                .summa(product.getPrice().multiply(BigDecimal.valueOf(newOrderDto.getItemsCount())))
                .build();

        order = orderRepository.save(order);

        return getOrderDto(newOrderDto, order, product);
    }

    public OrderDto updateOrder(Long id, NewOrderDto newOrderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(newOrderDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        order.setProduct(product);
        order.setItemsCount(newOrderDto.getItemsCount());
        order.setDate(newOrderDto.getDate());
        order.setSumma(product.getPrice().multiply(BigDecimal.valueOf(newOrderDto.getItemsCount())));

        orderProductRepository.deleteAll(order.getProducts());

        return getOrderDto(newOrderDto, order, product);
    }

    private OrderDto getOrderDto(NewOrderDto newOrderDto, Order order, Product product) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(newOrderDto.getItemsCount())
                .price(product.getPrice())
                .build();

        orderProductRepository.save(orderProduct);

        order.setProducts(List.of(orderProduct));
        orderRepository.save(order);

        return OrderDto.from(order);
    }

    public OrderDto deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        orderProductRepository.deleteAll(order.getProducts());
        orderRepository.delete(order);

        return OrderDto.from(order);
    }
}