package de.ait.pool.service;

import de.ait.pool.dto.orderDto.OrderProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.order.OrderProduct;
import de.ait.pool.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public List<OrderProduct> getOrderProductsByOrderId(Long orderId) {
        return orderProductRepository.findByOrderId(orderId);
    }

    public List<OrderProduct> getOrderProductsByProductId(Long productId) {
        return orderProductRepository.findByProductId(productId);
    }

    public OrderProductDto createOrderProduct(OrderProductDto orderProductDto) {
        OrderProduct orderProduct = orderProductDto.toEntity(null); // Assuming order will be set later
        return OrderProductDto.from(orderProductRepository.save(orderProduct));
    }

    public OrderProductDto updateOrderProduct(Long id, OrderProductDto orderProductDto) {
        OrderProduct orderProduct = orderProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found"));
        orderProduct.setProductId(orderProductDto.getProductId());
        orderProduct.setQuantity(orderProductDto.getQuantity());
        return OrderProductDto.from(orderProductRepository.save(orderProduct));
    }

    public OrderProductDto deleteOrderProduct(Long id) {
        OrderProduct orderProduct = orderProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found"));
        orderProductRepository.delete(orderProduct);
        return OrderProductDto.from(orderProduct);
    }
}
