package de.ait.pool.models;


import de.ait.pool.models.cart.Cart;
import de.ait.pool.models.cart.CartProduct;
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
    @Column (nullable = false)
    private Long id;

    @Column(length = 20)
    private String title;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column
    private String category;


    // Многие товары могут быть добавлены в разные корзины
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<CartProduct> cartProducts= new HashSet<>();

}
