package de.ait.pool.controller;

import de.ait.pool.controller.api.ProductApi;
import de.ait.pool.models.Product;
import de.ait.pool.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor


public class ProductController implements ProductApi {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Optional<Product> getById(@RequestParam Long id) {
        return productService.findById((long) id);
    }

    @GetMapping("/all")
    public List<Product> getProducts() {
        return productService.findAll();
    }

}
