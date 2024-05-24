package de.ait.pool.controller;


import de.ait.pool.dto.сartProductDto.CartProductDto;
import de.ait.pool.security.config.TestSecurityConfig;
import de.ait.pool.dto.cartDto.UpdateCartProductDto;
import de.ait.pool.dto.productDto.AddProductToCartDto;
import de.ait.pool.service.CartProductService;
import de.ait.pool.service.CartService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
@DisplayName("Endpoint /cart is working:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class CartProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private CartProductService cartProductService;

    @Nested
    @DisplayName("GET /cart/{cartId}:")
    public class GetCartProducts {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_401_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/cart/1"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails(value = "user")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_200_for_not_admin() throws Exception {
            mockMvc.perform(get("/api/cart/1"))
                    .andExpect(status().isOk());
        }

        @WithUserDetails(value = "admin")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_cart_products() throws Exception {
            Long cartId = 1L;
            Set<CartProductDto> cartProductDtos = Set.of(
                    new CartProductDto(1L, 1L, 1L, 2, "Product1")
            );

            when(cartProductService.getCartProducts(cartId)).thenReturn(cartProductDtos);

            mockMvc.perform(get("/api/cart/{cartId}", cartId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(cartProductDtos.size())));
        }
    }

    @Nested
    @DisplayName("GET /cart/{cartId}/cart-products/{cartProductId}:")
    public class GetCartProductById {

        @Test
        @WithUserDetails(value = "admin")
        @DisplayName("Return list of cart products")
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        void return_list_of_cart_products() throws Exception {
            Long cartId = 1L;
            Set<CartProductDto> cartProductDtos = Set.of(
                    new CartProductDto(1L, 1L, 1L, 2, "Product1")
            );

            when(cartProductService.getCartProducts(cartId)).thenReturn(cartProductDtos);

            mockMvc.perform(get("/api/cart/{cartId}", cartId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(cartProductDtos.size())));
        }
    }

    @Nested
    @DisplayName("PUT /cart/{cartId}/cart-products/{cartProductId}:")
    public class UpdateCartProduct {

        @WithUserDetails(value = "admin")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void update_cart_product_quantity() throws Exception {
            Long cartId = 1L;
            Long cartProductId = 1L;
            UpdateCartProductDto updateCartProductDto = new UpdateCartProductDto(5);
            CartProductDto updatedCartProductDto = new CartProductDto(1L, 1L, 1L, 5, "Product1");

            when(cartProductService.updateCartProduct(cartId, cartProductId, updateCartProductDto)).thenReturn(updatedCartProductDto);

            mockMvc.perform(put("/api/cart/{cartId}/cart-products/{cartProductId}", cartId, cartProductId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(updateCartProductDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.quantity", is(updatedCartProductDto.getQuantity())));
        }
    }

    @Nested
    @DisplayName("DELETE /cart/{cartId}/cart-products/{cartProductId}:")
    public class DeleteCartProduct {

        @WithUserDetails(value = "admin")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void delete_cart_product() throws Exception {
            Long cartId = 1L;
            Long cartProductId = 1L;
            CartProductDto cartProductDto = new CartProductDto(1L, 1L, 1L, 2, "Product1");

            when(cartProductService.deleteCartProduct(cartId, cartProductId)).thenReturn(cartProductDto);

            mockMvc.perform(delete("/api/cart/{cartId}/cart-products/{cartProductId}", cartId, cartProductId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(cartProductDto.getId().intValue())))
                    .andExpect(jsonPath("$.productName", is(cartProductDto.getProductName())));
        }
    }
    @Nested
    @DisplayName("POST /cart/{cartId}/products:")
    class AddProductToCart {

        @Test
        @DisplayName("Add product to cart")
        @WithUserDetails(value = "admin")
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        void add_product_to_cart() throws Exception {
            Long cartId = 1L;
            AddProductToCartDto addProductToCartDto = new AddProductToCartDto(1L, 3);
            CartProductDto cartProductDto = new CartProductDto(1L, 1L, 1L, 3, "Product1");

            when(cartService.addProductToCart(cartId, addProductToCartDto)).thenReturn(cartProductDto);

            mockMvc.perform(post("/api/cart/{cartId}/products", cartId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(addProductToCartDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.quantity", is(cartProductDto.getQuantity())))
                    .andExpect(jsonPath("$.productName", is(cartProductDto.getProductName())));
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}