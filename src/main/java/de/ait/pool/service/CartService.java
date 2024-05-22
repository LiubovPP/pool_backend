package de.ait.pool.service;
import de.ait.pool.dto.cartDto.CartDto;
import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

     private final CartProductRepository cartProductRepository;

    public CartDto updateCart(CartDto cartDto) {
        // Найти корзину по id
        Cart cart = cartRepository.findById(cartDto.getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        cart.getCartProducts().clear();

        cart.getCartProducts().addAll(
                cartDto.getCartProductDtos().stream().map(cartProductDto -> {
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setCart(cart);
                    cartProduct.setProduct(productRepository.findById(cartProductDto.getProductId())
                            .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Product not found")));
                    cartProduct.setQuantity(cartProductDto.getQuantity());
                    return cartProduct;
                }).collect(Collectors.toSet())
        );

        Cart savedCart = cartRepository.save(cart);
        return CartDto.fromCart(savedCart);
    }

    public CartProductDto getCartProductById(Long cartId, Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "CartProduct not found"));

        if (!cartProduct.getCart().getId().equals(cartId)) {
            throw new RestException(HttpStatus.NOT_FOUND, "CartProduct does not belong to the specified cart");
        }// ?? HttpStatus какой статус

        Product product = cartProduct.getProduct();
        return CartProductDto.builder()
                .productId(product.getId())
                .quantity(cartProduct.getQuantity())
                .build();
    }

    public CartProductDto addProductToCart(Long cartId, AddProductToCartDto addProductToCartDto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"Cart not found"));

        Product product = productRepository.findById(addProductToCartDto.getProductId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,"Product not found"));

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cartId, addProductToCartDto.getProductId());
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
}
