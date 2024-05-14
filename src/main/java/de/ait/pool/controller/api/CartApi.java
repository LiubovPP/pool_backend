package de.ait.pool.controller.api;

import de.ait.pool.dto.cartDto.CartDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/cart")
public interface CartApi {

    @Operation(summary = "Добавление продуктов в корзину", description = "Доступно авторизированному пользователю. По умолчанию роль - USER")
    @PutMapping
    public CartDto updateCart(CartDto cartDto);


}
