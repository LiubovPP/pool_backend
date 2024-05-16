package de.ait.pool.models.cart;
import de.ait.pool.models.Product;
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
    @Column(name = "id", nullable = false)
    private Long id;

    // Одна корзина принадлежит только одному пользователю
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    // Многие корзины могут содержать множество товаров
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products= new HashSet<>();

    @Override
    public String toString() {
        return String.format("Cart: ID - %d, products - %d items",
                id, products.size());
    }
}
