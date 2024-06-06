package de.ait.pool.service;

import de.ait.pool.dto.orderDto.NewOrderDto;
import de.ait.pool.dto.orderDto.OrderDto;
import de.ait.pool.dto.orderDto.OrderProductDto;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.models.order.Order;
import de.ait.pool.models.order.OrderProduct;
import de.ait.pool.repository.*;
import de.ait.pool.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartProductRepository cartProductRepository;


    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public OrderDto createOrder(AuthenticatedUser user, Set<CartProductDto> cartProducts) {
          /*  Order emptyOrder = Order.builder()
                    .date(LocalDateTime.now())
                    .user(userRepository.findById(user.getId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found")))
                    .summa(BigDecimal.ZERO)
                    .build();

           orderRepository.save(emptyOrder);

        // Преобразование DTO в сущности
        Set<OrderProduct> orderProducts = cartProducts.stream()
                .map(cartProductDto -> {
                    Product product = productRepository.findById(cartProductDto.getProductId())
                            .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Product not found"));

                    return OrderProduct.builder()
                            .product(product)
                            .quantity(cartProductDto.getQuantity())
                            .price(product.getPrice())
                            .order(emptyOrder)
                            .build();
                })
                .collect(Collectors.toSet());

        // Вычисление общей суммы заказа
        BigDecimal summa = orderProducts.stream()
                .map(orderProduct -> {
                    BigDecimal price = orderProduct.getPrice();
                    BigDecimal quantity = BigDecimal.valueOf(orderProduct.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Создание нового заказа
        Order order = Order.builder()
                .date(LocalDateTime.now())
                .summa(summa)
                .user(userRepository.findById(user.getId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"))) // Получаем пользователя из AuthenticatedUser
                .orderProducts(orderProducts)
                .build();

        orderProducts.forEach(orderProduct -> orderProduct.setOrder(order));
        orderRepository.save(order);


        // Удаление продуктов из корзины
        Set<Long> productIds = cartProducts.stream()
                .map(CartProductDto::getProductId)
                .collect(Collectors.toSet());
        cartProductRepository.deleteAllByProductIdIn(productIds);


        return OrderDto.from(order);*/
        return null;
    }
    }

   /* public OrderDto updateOrder(Long id, NewOrderDto newOrderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(newOrderDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        order.setProduct(product);
        order.setItemsCount(newOrderDto.getItemsCount());
        order.setDate(LocalDateTime.now());
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

        order.setProducts(Set.of(orderProduct));
        orderRepository.save(order);

        return OrderDto.from(order);
    }

    public OrderDto deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        orderProductRepository.deleteAll(order.getProducts());
        orderRepository.delete(order);

        return OrderDto.from(order);
    }*/
