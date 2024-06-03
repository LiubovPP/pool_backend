package de.ait.pool.controller.api;

import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tags(
        @Tag(name = "CartProduct-продукт в корзине")
)
@RequestMapping("/api/cart/cart-products")
public interface CartProductApi {

    @Operation(summary = "Получение списка всех продуктов в корзине", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    @GetMapping
    Set<CartProductDto> getCartProducts(@Parameter (hidden = true) @AuthenticationPrincipal AuthenticatedUser user);

    @Operation(summary = "Получение информации о продукте в корзине по идентификатору", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    @GetMapping("/{productId}")
    CartProductDto getCartProductById(@AuthenticationPrincipal AuthenticatedUser user,@PathVariable Long productId);

    @DeleteMapping("/{productId}")
    @Operation(summary = "Удаление продукта из корзины по идентификатору", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    public CartProductDto deleteCartProduct(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user,
                                            @PathVariable Long productId);

    @PostMapping("/product")
    @Operation(summary = "Добавление продукта в корзину", description = "Доступно авторизованному пользователю. По умолчанию роль - USER")
    public CartProductDto addProductToCart(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user, @RequestBody AddProductToCartDto addProductToCartDto);
}
