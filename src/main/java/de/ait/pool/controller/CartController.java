package de.ait.pool.controller;

import de.ait.pool.controller.api.CartApi;
import de.ait.pool.dto.cartDto.CartDto;
import de.ait.pool.models.Cart;
import de.ait.pool.models.Product;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.ProductRepository;
import de.ait.pool.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private CartService cartService;

    @Override
    public CartDto updateCart(CartDto cartDto) {
        return null;
    }

//    @Override
//    public CartDto updateCart(@RequestBody CartDto cartDto) {
//        Cart cart = cartRepository.findById(cartDto.getId())
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        List<Product> products = cartDto.getId().stream()
//                .map(id -> productRepository.findById(id)
//                        .orElseThrow(() -> new RuntimeException("Product not found")))
//                .collect(Collectors.toList());
//        cart.setProducts(cartDto.getProducts());
//        cartRepository.save(cart);
//        return cartDto;
//    }

//    @Override
//    public ResponseEntity<Cart> getCart(Long id) {
//        return cartService.getCartById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

//
//    @Override
//    public ResponseEntity<Void> deleteCart(Long id) {
//        cartService.deleteCart(id);
//        return ResponseEntity.ok().build();
//    }

}
