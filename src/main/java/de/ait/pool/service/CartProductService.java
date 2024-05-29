package de.ait.pool.service;

import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.UserRepository;
import de.ait.pool.security.details.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartProductService {
    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public Set<CartProductDto> getCartProducts(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        return cart.getCartProducts().stream().map(cartProduct -> {
            Product product = cartProduct.getProduct();
            return CartProductDto.builder()
                    .id(cartProduct.getId())
                    .cartId(cartProduct.getCart().getId())
                    .productId(product.getId())
                    .quantity(cartProduct.getQuantity())
                    .productName(cartProduct.getProduct().getTitle())
                    .build();
        }).collect(Collectors.toSet());
    }


    public CartProductDto deleteCartProduct(AuthenticatedUser authenticatedUser, Long productId) {
        Long currentUserId = authenticatedUser.getId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = cartRepository.findById(currentUser.getCart().getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId);

        cartProductRepository.delete(cartProduct);

        return CartProductDto.fromCartProduct(cartProduct);
}
}
