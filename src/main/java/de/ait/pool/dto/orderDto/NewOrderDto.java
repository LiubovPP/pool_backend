package de.ait.pool.dto.orderDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewOrderDto {
    @Schema(description = "Идентификатор пользователя", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "Идентификатор продукта", example = "1")
    @NotNull

    private Long productId;
    @Schema(description = "Колличество продукта", example = "1")
    @NotNull
    private int itemsCount;


    @NotNull
    private Date date;
}
