package de.ait.pool.controller;

import de.ait.pool.controller.api.CartProductApi;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.service.CartProductService;
import de.ait.pool.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CartProductController implements CartProductApi {

    private final CartService cartService;
    private final CartProductService cartProductService;

    @Override
    public Set<CartProductDto> getCartProducts(Long cartId) {
        return cartProductService.getCartProducts(cartId);
    }

    @Override
    public CartProductDto getCartProductById(Long cartId, Long cartProductId) {
        return cartService.getCartProductById(cartId, cartProductId);
    }

    @Override
    public CartProductDto updateCartProduct(@PathVariable Long cartId,
                                            @PathVariable Long cartProductId,
                                            @RequestBody UpdateCartProductDto updateCartProductDto) {
        return cartProductService.updateCartProduct(cartId, cartProductId, updateCartProductDto);
    }

    @Override
    public CartProductDto deleteCartProduct(Long cartId, Long cartProductId) {
        return cartProductService.deleteCartProduct(cartId, cartProductId);
    }

    @Override
    public CartProductDto addProductToCart(@PathVariable Long cartId, @RequestBody AddProductToCartDto addProductToCartDto) {
        return cartService.addProductToCart(cartId, addProductToCartDto);
    }


}

