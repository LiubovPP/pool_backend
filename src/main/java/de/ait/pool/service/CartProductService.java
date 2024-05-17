package de.ait.pool.service;

import de.ait.pool.dto.cartDto.CartProductDto;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.Product;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.repository.CartProductRepository;
import de.ait.pool.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartProductService {
private final CartProductRepository cartProductRepository;
private final CartRepository cartRepository;

    public CartProductDto updateCartProduct(Long cartId, Long cartProductId, UpdateCartProductDto updateCartProductDto) {
        // Проверяем, существует ли корзина
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Cart not found"));

        // Проверяем, существует ли продукт в корзине
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "CartProduct not found"));

        // Проверяем, принадлежит ли продукт указанной корзине
        if (!cartProduct.getCart().getId().equals(cartId)) {
            throw new RestException(HttpStatus.CONFLICT, "CartProduct does not belong to the specified cart");
        }

        // Обновляем количество продукта в корзине
        cartProduct.setQuantity(updateCartProductDto.getQuantity());
        cartProductRepository.save(cartProduct);

        Product product = cartProduct.getProduct();

        return CartProductDto.builder()
                .productId(product.getId())
                .quantity(cartProduct.getQuantity())
                .build();
    }

}
