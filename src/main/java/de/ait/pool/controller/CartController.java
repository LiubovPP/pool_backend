package de.ait.pool.controller;

import de.ait.pool.controller.api.CartApi;
import de.ait.pool.dto.cartDto.CartDto;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.ProductRepository;
import de.ait.pool.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {

    private CartService cartService;

    //TODO CartProduct cp = repo.findByCart_IdAndProduct_Id(cId, pId);
    //
    //cp.setQuantity(cp.getQuantity() + 1);
    //
    //repo.save(cp);

    @Override
    public CartDto updateCart(CartDto cartDto) {
        return cartService.updateCart(cartDto);
    }

}
