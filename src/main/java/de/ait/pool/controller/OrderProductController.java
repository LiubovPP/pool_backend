package de.ait.pool.controller;

import de.ait.pool.controller.api.OrderProductApi;
import de.ait.pool.dto.orderDto.OrderProductDto;
import de.ait.pool.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderProductController implements OrderProductApi {
    private final OrderProductService orderProductService;

    @Override
    public List<OrderProductDto> getOrderProductsByOrderId(@PathVariable Long orderId) {
        return OrderProductDto.from(orderProductService.getOrderProductsByOrderId(orderId));
    }

    @Override
    public List<OrderProductDto> getOrderProductsByProductId(@PathVariable Long productId) {
        return OrderProductDto.from(orderProductService.getOrderProductsByProductId(productId));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public OrderProductDto createOrderProduct(@RequestBody @Valid OrderProductDto orderProductDto) {
        return orderProductService.createOrderProduct(orderProductDto);
    }

    @Override
    public OrderProductDto updateOrderProduct(@PathVariable Long id, @RequestBody @Valid OrderProductDto orderProductDto) {
        return orderProductService.updateOrderProduct(id, orderProductDto);
    }

    @Override
    public OrderProductDto deleteOrderProduct(@PathVariable Long id) {
        return orderProductService.deleteOrderProduct(id);
    }
}
