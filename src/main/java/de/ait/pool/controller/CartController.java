package de.ait.pool.controller;

import de.ait.pool.controller.api.CartApi;
import de.ait.pool.dto.cartDto.CartDto;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.ProductRepository;
import de.ait.pool.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private CartService cartService;

    //TODO CartProduct cp = repo.findByCart_IdAndProduct_Id(cId, pId);
    //
    //cp.setQuantity(cp.getQuantity() + 1);
    //
    //repo.save(cp);

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
