package de.ait.pool.controller;

import de.ait.pool.controller.api.CartProduct;
import de.ait.pool.dto.cartDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.service.CartProductService;
import de.ait.pool.service.CartService;
import de.ait.pool.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CartProductController implements CartProduct {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final CartProductService cartProductService;

    @Override
    public Set<CartProductDto> getCartProducts(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        return cart.getCartProducts().stream().map(cartProduct -> {
            Product product = cartProduct.getProduct();
            return CartProductDto.builder()
                    .productId(product.getId())
                    .quantity(cartProduct.getQuantity())
                    .build();
        }).collect(Collectors.toSet());
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


}

