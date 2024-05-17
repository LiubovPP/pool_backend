package de.ait.pool.controller.api;

import de.ait.pool.dto.cartDto.CartProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;

@Tags(
        @Tag(name = "CartProduct-продукт в корзине")
)
@RequestMapping("/api/cart")
public interface CartProduct {

//GET /api/cart/{cartId}/cart-products - Получение списка всех продуктов в корзине.
@Operation(summary = "Получение списка всех продуктов в корзине", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
@GetMapping("/{cartId}")
public Set<CartProductDto> getCartProducts(@PathVariable Long cartId);


//GET /api/cart/{cartId}/cart-products/{cartProductId} - Получение информации о продукте в корзине по идентификатору.
//PUT /api/cart/{cartId}/cart-products/{cartProductId} - Обновление информации о продукте в корзине (например, количество).
//DELETE /api/cart/{cartId}/cart-products/{cartProductId} - Удаление продукта из корзины по идентификатору.


}
