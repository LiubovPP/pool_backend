package de.ait.pool.dto.productDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "AddProductToCartDto", description = "Данные для добаления продукта в корзину")
public class AddProductToCartDto {


    @Schema(description = "Идентификатор продукта", example = "1")
    @NotNull(message = "Цена обязательна")
    private Long productId;

    @Schema(description = "Количество продукта", example = "1")
    private int quantity;

}