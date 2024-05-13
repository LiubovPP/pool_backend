package de.ait.pool.controller;

import de.ait.pool.controller.api.CartApi;
import de.ait.pool.models.Cart;
import de.ait.pool.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {
    private CartService cartService;

//    @Override
//    public ResponseEntity<Cart> getCart(Long id) {
//        return cartService.getCartById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @Override
//    public ResponseEntity<Cart> createCart(Cart cart) {
//        Cart savedCart = cartService.saveCart(cart);
//        return ResponseEntity.ok(savedCart);
//    }
//
//    @Override
//    public ResponseEntity<Void> deleteCart(Long id) {
//        cartService.deleteCart(id);
//        return ResponseEntity.ok().build();
//    }

}
