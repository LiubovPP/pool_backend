package de.ait.pool.dto.cartDto;

import de.ait.pool.models.Cart;
import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    private Set<Product> products;

    public static CartDto fromCart(Cart cart) {
        return CartDto.builder()
                // .id(cart.getId()) ??? TODO
                .user(cart.getUser())
                .products(cart.getProducts())
                .build();
    }

}