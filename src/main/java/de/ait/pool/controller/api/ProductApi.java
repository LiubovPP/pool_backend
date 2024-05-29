package de.ait.pool.controller.api;

import de.ait.pool.dto.productDto.NewProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@Tags (
        @Tag(name = "Products")
)
@RequestMapping("/api/products")
public interface ProductApi {



        @Operation(summary = "Продукт по id", description = "Доступно администратору. По умолчанию роль - ADMIN")
        @GetMapping("/{id}")
        Optional<ProductDto> getById(@PathVariable Long id); // Use PathVariable for id

        @Operation(summary = "Список продуктов", description = "Доступно всем. По умолчанию роль - USER")
        @GetMapping
        List<ProductDto> getProducts();

        @Operation(summary = "Создать продукт", description = "Доступно администратору. По умолчанию роль - ADMIN")
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        ProductDto createProduct(@RequestBody @Valid NewProductDto newProductDto);

        @Operation(summary = "Обновить продукт", description = "Доступно администратору. По умолчанию роль - ADMIN")
        @PutMapping("/{id}")
        ProductDto updateProduct(@PathVariable Long id, @RequestBody @Valid NewProductDto newProductDto);

        @Operation(summary = "Удалить продукт", description = "Доступно администратору. По умолчанию роль - ADMIN")
        @DeleteMapping("/{id}")
        ProductDto deleteProduct(@PathVariable Long id);

}
