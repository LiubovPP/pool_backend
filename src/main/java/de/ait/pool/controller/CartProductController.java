package de.ait.pool.controller;

import de.ait.pool.controller.api.CartProductApi;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.UserRepository;
import de.ait.pool.security.details.AuthenticatedUser;
import de.ait.pool.service.CartProductService;
import de.ait.pool.service.CartService;
import de.ait.pool.service.OrderService;
import de.ait.pool.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CartProductController implements CartProductApi {

    private final CartService cartService;
    private final CartProductService cartProductService;
    private final UsersService usersService;

    @Override
    public Set<CartProductDto> getCartProducts(AuthenticatedUser user) {
        Long currentUserId = user.getId();
        UserDto currentUser = usersService.getUserById(currentUserId);
        return cartService.getCartProducts(currentUser);
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

