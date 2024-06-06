package de.ait.pool.controller;

import de.ait.pool.controller.api.ProductApi;
import de.ait.pool.dto.productDto.NewProductDto;
import de.ait.pool.dto.productDto.ProductDto;
import de.ait.pool.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor


public class ProductController implements ProductApi {
    private final ProductService productService;




    public Optional<ProductDto> getById(@PathVariable Long id) {
        return productService.findById(id).map(ProductDto::from);
   }

    @Override

    public List<ProductDto> getProducts() {
        return ProductDto.from(productService.findAll());
    }

    @Override

    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid NewProductDto newProductDto) {
        return productService.createProduct(newProductDto);
    }

    @Override

    public ProductDto updateProduct(@PathVariable Long id, @RequestBody @Valid NewProductDto newProductDto) {
        return productService.updateProduct(id, newProductDto);
    }

    @Override
    public ProductDto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
