package de.ait.pool.dto.orderDto;

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
    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal summa;

    @NotNull
    private int itemsCount;

    @NotNull
    private Date date;
}
