package de.ait.pool.models.cart;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.ait.pool.models.User;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // Одна корзина принадлежит только одному пользователю
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;


    // Многие корзины могут содержать множество товаров
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartProduct> cartProducts = new HashSet<>();



    @Override
    public String toString() {
        return String.format("Cart: ID - %d, products - %d items",
                id, cartProducts.size());
    }
}
