package de.ait.pool.controller;

import de.ait.pool.controller.api.CartApi;
import de.ait.pool.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {

    private final CartService cartService;
    /*@Override
    public CartDto getCart(Long userId) {
        return null;
    }

    @Override
    public CartDto createCart(CreateCartRequest createCartRequest) {
        return null;
    }

    @Override
    public CartDto addProductToCart(Long cartId, AddToCartRequest addToCartRequest) {
        return null;
    }

    @Override
    public CartDto removeProductFromCart(Long cartId, Long productId) {
        return null;
    }

    @Override
    public CartDto updateProductQuantityInCart(Long cartId, Long productId, UpdateProductQuantityRequest updateRequest) {
        return null;
    }

    @Override
    public Set<CartProductDto> getProductsInCart(Long cartId) {
        return Set.of();
    }*/

    //TODO CartProduct cp = repo.findByCart_IdAndProduct_Id(cId, pId);
    //
    //cp.setQuantity(cp.getQuantity() + 1);
    //
    //repo.save(cp);


}
