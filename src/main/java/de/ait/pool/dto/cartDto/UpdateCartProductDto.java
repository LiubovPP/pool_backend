package de.ait.pool.dto.cartDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UpdateCartProduct", description = "Количество продукта в корзине пользователя")
public class UpdateCartProductDto {
    @Schema(description = "Количество продукта", example = "2")
    private Integer quantity;
}
