package de.ait.pool.service;
import de.ait.pool.dto.cartDto.CartDto;
import de.ait.pool.dto.cartDto.CartProductDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
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

/*    @Transactional
    public void addProductToCart(CartProductDto cartProductDto) {
        Cart cart = cartRepository.findById(cartProductDto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(cartProductDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(cartProductDto.getQuantity());

        cartProductRepository.save(cartProduct);
    }

    public CartDto updateCart(CartDto cartDto) {

    }*/

   /* @Transactional(readOnly = true)
    @Override
    public CartDto getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setCustomerName(cart.getCustomerName());

        Set<CartProductDto> cartProductDTOs = cart.getCartProducts().stream().map(cartProduct -> {
            CartProductDTO cartProductDTO = new CartProductDTO();
            cartProductDTO.setId(cartProduct.getId());
            cartProductDTO.setCartId(cartProduct.getCart().getId());
            cartProductDTO.setProductId(cartProduct.getProduct().getId());
            cartProductDTO.setQuantity(cartProduct.getQuantity());
            return cartProductDTO;
        }).collect(Collectors.toSet());

        cartDTO.setCartProducts(cartProductDTOs);

        return cartDTO;
    }*/
}
