package de.ait.pool.controller.api;

import de.ait.pool.dto.cartDto.CartDto;
import de.ait.pool.dto.cartDto.CartProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tags(
        @Tag(name = "Cart")
)
@RequestMapping("/api/cart")
public interface CartApi {

//Примеры конечных точек (endpoints):
//
//Корзина (Cart):
//
//GET /api/cart/{userId} - Получение информации о корзине по идентификатору пользователя.
//POST /api/cart - Создание новой корзины для пользователя.
//POST /api/cart/{cartId}/products - Добавление продукта в корзину.
//DELETE /api/cart/{cartId}/products/{productId} - Удаление продукта из корзины.
//PUT /api/cart/{cartId}/products/{productId} - Обновление количества продукта в корзине.
//GET /api/cart/{cartId}/products - Получение списка продуктов в корзине.


   /* @GetMapping("/{userId}")
    public CartDto getCart(@PathVariable Long userId);

    @PostMapping
    public CartDto createCart(@RequestBody CreateCartRequest createCartRequest);

    @PostMapping("/{cartId}/products")
    public CartDto addProductToCart(@PathVariable Long cartId, @RequestBody AddToCartRequest addToCartRequest);

    @DeleteMapping("/{cartId}/products/{productId}")
    public CartDto removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId);


    @PutMapping("/{cartId}/products/{productId}")
    public CartDto updateProductQuantityInCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestBody UpdateProductQuantityRequest updateRequest);


    @GetMapping("/{cartId}/products")
    public Set<CartProductDto> getProductsInCart(@PathVariable Long cartId);
*/


}
