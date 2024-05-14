package de.ait.pool.models;


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
    @ManyToMany(mappedBy = "products")
    private Set<Cart> carts= new HashSet<>();

}
