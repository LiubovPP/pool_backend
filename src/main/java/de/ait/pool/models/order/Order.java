package de.ait.pool.models.order;

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

    @Column (nullable = false)
    private Long userId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal summa;

    @Column (nullable = false)
    private int itemsCount;

    @Column (nullable = false)
   // @Temporal(TemporalType.TIMESTAMP) // Явное указание типа даты
    private Date date;

    //@OneToMany(mappedBy = "order")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //Установка cascade и fetch параметров в ассоциации @OneToMany, если у вас есть особые требования к каскадным операциям или режиму выборки
    private List<OrderProduct> products;

}
