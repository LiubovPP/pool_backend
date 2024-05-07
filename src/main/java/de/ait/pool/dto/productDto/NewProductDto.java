package de.ait.pool.dto.productDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.transform.Source;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "NewProduct", description = "Данные для создания продукта")
public class NewProductDto  {

    @Schema(description = "Название продукта", example = "услуга/химия")
    @NotBlank(message = "Название обязательно")
    @Size(max = 20, message = "Длина названия должна быть меньше или равна 20 символам")
    private String title;

    @Schema(description = "Цена продукта", example = "19.99")
    @NotNull(message = "Цена обязательна")

    private BigDecimal price;

    @Schema(description = "Категория продукта", example = "услуга/химия")
    private String category;


}