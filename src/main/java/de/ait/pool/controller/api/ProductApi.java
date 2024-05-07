package de.ait.pool.controller.api;

import de.ait.pool.dto.StandardResponseDto;
import de.ait.pool.dto.UserDto;
import de.ait.pool.models.Product;

import de.ait.pool.validation.dto.ValidationErrorsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;

@RequestMapping("/api/products")
public interface ProductApi {

        @Operation(summary = "Продукт по id ", description = "Доступно администратору. По умолчанию роль - ADMIN")

        @GetMapping("/{id}")
        public Optional<Product> getById(@RequestParam Long id);

        @Operation(summary = "Список продуктов", description = "Доступно всем. По умолчанию роль - USER")
        @GetMapping("/all")
        public List<Product> getProducts();


}
