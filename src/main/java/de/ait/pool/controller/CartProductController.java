package de.ait.pool.controller;

import de.ait.pool.controller.api.CartProductApi;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.security.details.AuthenticatedUser;
import de.ait.pool.service.CartService;
import de.ait.pool.service.UsersService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CartProductController implements CartProductApi {

    private final CartService cartService;
    private final UsersService usersService;

    @Override
    public Set<CartProductDto> getCartProducts(AuthenticatedUser user) {
        Long currentUserId = user.getId();
        UserDto currentUser = usersService.getUserById(currentUserId);
        return cartService.getCartProducts(currentUser);
    }

    @Override
    public CartProductDto getCartProductById(@Parameter(hidden = true) AuthenticatedUser user, Long productId) {
        return cartService.getCartProductById(user,productId);
    }

    //@CrossOrigin(origins = "*", allowedHeaders = "*")
    @Override
    public CartProductDto deleteCartProduct(@Parameter(hidden = true) AuthenticatedUser user, Long productId) {
        return cartService.deleteCartProduct(user, productId);
    }

    @Override
    public CartProductDto addProductToCart(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user, @RequestBody AddProductToCartDto addProductToCartDto) {
        return cartService.addProductToCart(user, addProductToCartDto);
    }


}

