package de.ait.pool.controller.api;

import de.ait.pool.dto.cartDto.CartDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.*;

@Tags(
        @Tag(name = "Cart")
)
@RequestMapping("/api/cart")
public interface CartApi {

//
//  @GetMapping("/{userId}")
//    public CartDto getCart(@PathVariable Long userId);
  /*
    @PostMapping
    public CartDto createCart(@RequestBody CreateCartRequest createCartRequest);

    @PostMapping("/{cartId}/products")
    public CartDto addProductToCart(@PathVariable Long cartId, @RequestBody AddToCartRequest addToCartRequest);

    @DeleteMapping("/{cartId}/products/{productId}")
    public CartDto removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId);


    @PutMapping("/{cartId}/products/{productId}")
    public CartDto updateProductQuantityInCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestBody UpdateProductQuantityRequest updateRequest);


    @GetMapping("/{cartId}/products")
    public Set<CartProductDto> getProductsInCart(@PathVariable Long cartId);
*/


}
