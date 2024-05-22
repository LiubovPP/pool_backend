package de.ait.pool.dto.сartProductDto;

import de.ait.pool.models.cart.CartProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CartProduct", description = "Данные продукта в корзине пользователя")
public class CartProductDto {
    @Schema(description = "идентификатор продукта в корзине", example = "1")
    private Long id;

    @Schema(description = "идентификатор корзины", example = "1")
    private Long cartId;

    @Schema(description = "идентификатор продукта", example = "1")
    private Long productId;

    @Schema(description = "Количество продукта", example = "2")
    private Integer quantity;

    @Schema(description = "Название продукта", example = "chemie")
    private String productName;

    public static CartProductDto fromCartProduct(CartProduct cartProduct) {
        return CartProductDto.builder()
                .id(cartProduct.getId())
                .cartId(cartProduct.getCart().getId())
                .productId(cartProduct.getProduct().getId())
                .productName(cartProduct.getProduct().getTitle())
                .quantity(cartProduct.getQuantity())
                .build();
    }
}
