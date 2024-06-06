package de.ait.pool.service;

import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.ProductRepository;
import de.ait.pool.repository.UserRepository;
import de.ait.pool.security.details.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartProductRepository cartProductRepository;

    private final UserRepository userRepository;

    public CartProductDto getCartProductById(AuthenticatedUser authenticatedUser, Long productId) {
        Long currentUserId = authenticatedUser.getId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = cartRepository.findById(currentUser.getCart().getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (!cartProduct.getCart().getId().equals(cart.getId())) {
            throw new RestException(HttpStatus.NOT_FOUND, "CartProduct does not belong to the specified cart");
        }

        Product product = cartProduct.getProduct();
        return CartProductDto.builder()
                .cartId(cart.getId())
                .productId(product.getId())
                .quantity(cartProduct.getQuantity())
                .productName(product.getTitle())
                .build();
    }

    public CartProductDto addProductToCart(AuthenticatedUser authenticatedUser, AddProductToCartDto addProductToCartDto) {
        Long currentUserId = authenticatedUser.getId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = cartRepository.findById(currentUser.getCart().getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        Product product = productRepository.findById(addProductToCartDto.getProductId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Product not found"));

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), addProductToCartDto.getProductId());
        if (cartProduct == null) {
            cartProduct = CartProduct.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(addProductToCartDto.getQuantity())
                    .build();
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + addProductToCartDto.getQuantity());
        }

        cartProductRepository.save(cartProduct);
        cartRepository.save(cart);
        return CartProductDto.fromCartProduct(cartProduct);
    }

    public Set<CartProductDto> getCartProducts(UserDto user) {
        Long currentUserId = user.getId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"User not found"));

        Set<CartProduct> cartProducts = cartProductRepository.findByCart(currentUser.getCart());
        return cartProducts.stream()
                .map(CartProductDto::fromCartProduct)
                .collect(Collectors.toSet());
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
