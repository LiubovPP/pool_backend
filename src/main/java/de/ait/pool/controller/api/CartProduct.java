package de.ait.pool.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.RequestMapping;

@Tags(
        @Tag(name = "CartProduct-продукт в корзине")
)
@RequestMapping("/api/cart")
public interface CartProduct {
//Продукт в корзине (CartProduct):
//
//GET /api/cart/{cartId}/cart-products - Получение списка всех продуктов в корзине.
//GET /api/cart/{cartId}/cart-products/{cartProductId} - Получение информации о продукте в корзине по идентификатору.
//PUT /api/cart/{cartId}/cart-products/{cartProductId} - Обновление информации о продукте в корзине (например, количество).
//DELETE /api/cart/{cartId}/cart-products/{cartProductId} - Удаление продукта из корзины по идентификатору.
}
