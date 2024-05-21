package de.ait.pool.controller.api;

import de.ait.pool.dto.cartDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Tags(
        @Tag(name = "CartProduct-продукт в корзине")
)
@RequestMapping("/api/cart")
public interface CartProduct {
    //GET /api/cart/{userId} - Получение информации о корзине по идентификатору пользователя.
    //POST /api/cart - Создание новой корзины для пользователя.
    //POST /api/cart/{cartId}/products - Добавление продукта в корзину.
    //DELETE /api/cart/{cartId}/products/{productId} - Удаление продукта из корзины.
    //PUT /api/cart/{cartId}/products/{productId} - Обновление количества продукта в корзине.
    //GET /api/cart/{cartId}/products - Получение списка продуктов в корзине.

    @Operation(summary = "Получение списка всех продуктов в корзине", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    @GetMapping("/{cartId}")
    public Set<CartProductDto> getCartProducts(@PathVariable Long cartId);

    @Operation(summary = "Получение информации о продукте в корзине по идентификатору", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    @GetMapping("/{cartId}/cart-products/{cartProductId}")
    CartProductDto getCartProductById(@PathVariable Long cartId, @PathVariable Long cartProductId);

    @PutMapping("/{cartId}/cart-products/{cartProductId}")
    @Operation(summary = "Обновление информации о продукте - количества - в корзине", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    public CartProductDto updateCartProduct(@PathVariable Long cartId,
                                            @PathVariable Long cartProductId,
                                            @RequestBody UpdateCartProductDto updateCartProductDto);

    @DeleteMapping("/{cartId}/cart-products/{cartProductId}")
    @Operation(summary = "Удаление продукта из корзины по идентификатору", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    public CartProductDto deleteCartProduct(@PathVariable Long cartId,
                                            @PathVariable Long cartProductId);

}
