package de.ait.pool.dto.cartDto;

import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Cart", description = "Данные корзины пользователя")
public class CartDto {
    @Schema(description = "идентификатор корзины", example = "1")
    private Long id;
    @Schema(description = "User корзины", example = "Max Mustermann")
    private User user;
    @Schema(description = "Список продуктов", example = "Услуга или химия")
    private Set<CartProductDto> cartProductDtos;

    public static CartDto fromCart(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .user(cart.getUser()) // Получение имени пользователя
                .cartProductDtos(cart.getCartProducts().stream()
                        .map(CartProductDto::fromCartProduct)
                        .collect(Collectors.toSet()))
                .build();
    }

}
