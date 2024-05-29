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
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public CartProductDto getCartProductById(@Parameter(hidden = true) AuthenticatedUser user, Long productId) {
        return cartService.getCartProductById(user,productId);
    }

    @Override
    public CartProductDto deleteCartProduct(AuthenticatedUser user, Long productId) {
        return cartProductService.deleteCartProduct(user, productId);
    }

    @Override
    public CartProductDto addProductToCart(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user, @RequestBody AddProductToCartDto addProductToCartDto) {
        return cartService.addProductToCart(user, addProductToCartDto);
    }


}

