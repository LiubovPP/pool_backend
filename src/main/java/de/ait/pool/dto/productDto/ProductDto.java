package de.ait.pool.dto.productDto;

import de.ait.pool.models.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Product", description = "Данные о продукте")
public class ProductDto {

    @Schema(description = "Идентификатор продукта", example = "1")
    private Long id;

    @Schema(description = "Название продукта", example = "услуга/химия")
    @NotBlank(message = "Название обязательно")
    @Size(max = 20, message = "Длина названия должна быть меньше или равна 20 символам")
    private String title;

    @Schema(description = "Цена продукта", example = "19.99")
    @NotNull(message = "Цена обязательна")

    private BigDecimal price;

    @Schema(description = "Категория продукта", example = "услуга/химия")
    private String category;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .category(product.getCategory())
                .build();
    }

    public static List<ProductDto> from(Collection<Product> products) {
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }
}