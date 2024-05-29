package de.ait.pool.service;

import de.ait.pool.dto.orderDto.OrderProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.order.Order;
import de.ait.pool.models.order.OrderProduct;
import de.ait.pool.repository.OrderProductRepository;
import de.ait.pool.repository.OrderRepository;
import de.ait.pool.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<OrderProduct> getOrderProductsByOrderId(Long orderId) {
        return orderProductRepository.findByOrderId(orderId);
    }

    public List<OrderProduct> getOrderProductsByProductId(Long productId) {
        return orderProductRepository.findByProductId(productId);
    }

    public OrderProductDto createOrderProduct(OrderProductDto orderProductDto) {
        Order order = orderRepository.findById(orderProductDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(orderProductDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(orderProductDto.getQuantity())
                .price(orderProductDto.getPrice())
                .build();

        return OrderProductDto.from(orderProductRepository.save(orderProduct));
    }

    public OrderProductDto updateOrderProduct(Long id, OrderProductDto orderProductDto) {
        OrderProduct orderProduct = orderProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found"));

        Product product = productRepository.findById(orderProductDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        orderProduct.setProduct(product);
        orderProduct.setQuantity(orderProductDto.getQuantity());
        orderProduct.setPrice(orderProductDto.getPrice());

        return OrderProductDto.from(orderProductRepository.save(orderProduct));
    }

    public OrderProductDto deleteOrderProduct(Long id) {
        OrderProduct orderProduct = orderProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found"));
        orderProductRepository.delete(orderProduct);
        return OrderProductDto.from(orderProduct);
    }
}