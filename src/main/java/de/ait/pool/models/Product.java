package de.ait.pool.models;


import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
import de.ait.pool.models.order.OrderProduct;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 20)
    private String title;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column
    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartProduct> cartProducts = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new HashSet<>();


    public Product(Long productId, String testTitle, BigDecimal testPrice, String testCategory) {
        this.id = productId;
        this.title = testTitle;
        this.price = testPrice;
        this.category = testCategory;
    }
}
