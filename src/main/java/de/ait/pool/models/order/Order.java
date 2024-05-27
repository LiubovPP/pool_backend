package de.ait.pool.models.order;

import de.ait.pool.models.Product;
import de.ait.pool.models.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    //@OneToMany(mappedBy = "order")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> products;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal summa;

    @Column (nullable = false)
    private int itemsCount;

    @Column (nullable = false)
    private Date date;








}
